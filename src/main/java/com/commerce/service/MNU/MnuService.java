package com.commerce.service.MNU;

import com.commerce.comm.CamelKeyMap;
import com.commerce.comm.ObjectMapperUtils;
import com.commerce.comm.UserVO;
import com.commerce.comm.UtilMapper;
import com.commerce.exception.UserException;
import com.commerce.module.MEM.vo.SMEM001SVO;
import com.commerce.module.MEM.vo.SMEM006RVO;
import com.commerce.service.MNU.vo.MNU0101S01R;
import com.commerce.service.MNU.vo.MNU0101S01S;
import com.commerce.service.MNU.vo.MNUMenu;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional
@Service("MnuService")
public class MnuService extends UtilMapper {
    @Autowired
    private HttpSession session;
    public boolean MNU0101S01(MNU0101S01S req, MNU0101S01R rsp) throws UserException {
        Map<String, Object> map = objectMapper.convertValue(req, Map.class);

        List<CamelKeyMap> result = generalMapper.selectList("MEN","selectMnuList",map);

        List<MNUMenu> convertList = ObjectMapperUtils.convertToList(result, MNUMenu.class);

        String type = StringUtils.isNotEmpty(req.getType()) ? req.getType() : "";


        List<MNUMenu> amdList = getHierarchicalMenu(convertList, "ADM", type);

        List<MNUMenu> amdMappingList = getHierarchicalMenu(convertList, "ADM", "LIST");

        // 조회된 메뉴 Tree구조로 변경
        rsp.setADM(amdList);
        rsp.setAdmMappingList(amdMappingList);

        return true;
    }

    public boolean MNU0101S02(MNU0101S01S req, MNU0101S01R rsp) throws UserException {
        Map<String, Object> map = objectMapper.convertValue(req, Map.class);

        List<CamelKeyMap> result = generalMapper.selectList("MEN","selectMnuList",map);

        List<MNUMenu> convertList = ObjectMapperUtils.convertToList(result, MNUMenu.class);
        // 조회된 메뉴 Tree구조로 변경
        rsp.setADM(convertList);

        return true;
    }


    public boolean MNU0101U02(MNU0101S01S req) throws UserException {
        Map<String, Object> map = objectMapper.convertValue(req, Map.class);

        UserVO userVo = (UserVO) session.getAttribute("user");
        String userId = userVo.getCurrentSessionId();

        map.put("lastUserId", userId);
        int result = generalMapper.insert("MEN","insertMenu",map);


        return result > 0;
    }


    /**
     * 트리구조 메뉴생성
     * @param list
     * @param sysDivCd
     * @param type
     * @return
     */
    private List<MNUMenu> getHierarchicalMenu(List<MNUMenu> list, String sysDivCd, String type) {
        // sysDivCd에 따른 필터링 후 리스트 정렬 (1.upperMenuCode == null, 2.menuSeq ASC)
        List<MNUMenu> sortedList = list.stream()
                .filter(item -> sysDivCd.equals(item.getSysDivCd()))
                .sorted()
                .collect(Collectors.toList());

        // 리스트를 hashMap으로 변환
        Map<String, MNUMenu> menuMap = sortedList.stream()
                .collect(Collectors.toMap(MNUMenu::getMenuCode, menu -> menu));

        List<MNUMenu> naviList = sortedList.stream()
                .map(menu -> {
                    buildNavigator(menu, menuMap);
                    return menu;
                })
                .collect(Collectors.toList());

        if("LIST".equals(type)) {
            return sortedList;
        }

        // 리스트 hierachy 처리
        List<MNUMenu> rootMenus = new ArrayList<>();
        for(MNUMenu menu : naviList) {
            if(menu.getUpperMenuCode() == null) {
                rootMenus.add(menu);

            } else {
                MNUMenu parent = menuMap.get(menu.getUpperMenuCode());
                // 메뉴코드의 upperMenuCode가 등록되어 있지 않은경우 해당메뉴는 처리하지 않는다
                if((!menu.getMenuDepth().equals("0")) && parent == null) {
                    continue;
                }

                if(parent.getChildren() == null) {
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(menu);
            }
        }

        return rootMenus;
    }
    /**
     * 네비게이션생성
     * @param menu
     * @param menuMap
     */
    private void buildNavigator(MNUMenu menu, Map<String, MNUMenu> menuMap) {
        List<String> naviList = new ArrayList<>();
        MNUMenu currentMenu = menu;

        // upperMenuCode가 있을경우 parent를 찾아서 naviList에서 추가
        while(currentMenu != null) {
            naviList.add(currentMenu.getMenuName());
            currentMenu = menuMap.get(currentMenu.getUpperMenuCode());
        }

        // List 역정렬
        Collections.reverse(naviList);
        menu.setNavigator(naviList);
    }
}
