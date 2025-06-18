package com.commerce.service.MNU;

import com.commerce.comm.CamelKeyMap;
import com.commerce.comm.ObjectMapperUtils;
import com.commerce.comm.UtilMapper;
import com.commerce.exception.UserException;
import com.commerce.module.COM.COMService;
import com.commerce.service.HCO.vo.AdminVO;
import com.commerce.service.MNU.vo.MNU0101S01R;
import com.commerce.service.MNU.vo.MNU0101S01S;
import com.commerce.service.MNU.vo.MNU0201S01S;
import com.commerce.service.MNU.vo.MNUMenu;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service("MnuService")
public class MnuService extends UtilMapper {

    @Autowired
    private COMService comService;
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

        rsp.setADM(convertList);

        return true;
    }


    public boolean MNU0101U02(MNU0101S01S req) throws UserException {
        int resultCnt = 0;
        AdminVO userVo = comService.getAdminInfo();
        for (MNU0101S01S menu : req.getMenuList()) {
            Map<String, Object> map = objectMapper.convertValue(menu, Map.class);
            String userId = userVo.getId();

            map.put("lastUserId", userId);
            map.put("rgtrUserId", userId);

            if ("C".equals(map.get("rowStatus"))) {
                resultCnt += generalMapper.insert("MEN", "insertMenu",map );
            } else if ("U".equals(map.get("rowStatus"))) {
                resultCnt += generalMapper.insert("MEN", "updateMenu",map );
            } else if ("D".equals(map.get("rowStatus"))) {
                resultCnt += generalMapper.insert("MEN", "deleteMenu", map);
            }
        }

        return resultCnt > 0;
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



    public boolean MNU0201S01(MNU0201S01S req, MNU0101S01R rsp) throws UserException {
        Map<String, Object> map = objectMapper.convertValue(req, Map.class);

        List<CamelKeyMap> result = generalMapper.selectList("MEN","selectMnuListTemp",map);

        List<MNUMenu> convertList = ObjectMapperUtils.convertToList(result, MNUMenu.class);

        String type = StringUtils.isNotEmpty(req.getSystemType()) ? req.getSystemType() : "";


        List<MNUMenu> amdList = getHierarchicalMenu(convertList, "ADM", type);

        List<MNUMenu> amdMappingList = getHierarchicalMenu(convertList, "ADM", "LIST");

        // 조회된 메뉴 Tree구조로 변경
        rsp.setADM(amdList);
        rsp.setAdmMappingList(amdMappingList);

        return true;
    }

    public boolean MNU0201S02(MNU0201S01S req, MNU0101S01R rsp) throws UserException {
        Map<String, Object> map = objectMapper.convertValue(req, Map.class);

        List<CamelKeyMap> result = generalMapper.selectList("MEN","selectMenuListTemp",map);

        List<MNUMenu> convertList = ObjectMapperUtils.convertToList(result, MNUMenu.class);

//        for(MNUMenu menu : convertList) {
//            makeChildMenu(menu, convertList);
//        }
//
//        // 정렬
//        List<MNUMenu> topLevelMenus = convertList.stream()
//                .filter(menu -> menu.getParentMenuId() == null)
//                .collect(Collectors.toList());


        rsp.setADM(convertList);

        return true;
    }

    public void makeChildMenu(MNUMenu childMenu, List<MNUMenu> menuList) {
        menuList.stream()
                .filter(menu -> Objects.equals(childMenu.getParentMenuId(), menu.getMenuId()))
                .findFirst()
                .ifPresent(parent -> {
                    parent.setChildren(
                            Optional.ofNullable(parent.getChildren()).orElseGet(ArrayList::new)
                    );
                    parent.getChildren().add(childMenu);
                });
    }

    public boolean MNU0201U02(MNU0201S01S req) throws UserException {
        int resultCnt = 0;
        AdminVO userVo = comService.getAdminInfo();
        for (MNU0201S01S menu : req.getMenuList()) {
            Map<String, Object> map = objectMapper.convertValue(menu, Map.class);
            String userId = userVo.getId();

            map.put("lastUserId", userId);
            map.put("rgtrUserId", userId);

            if ("C".equals(map.get("rowStatus"))) {
                resultCnt += generalMapper.insert("MEN", "insertMenuTemp",map );
                if((Integer)map.get("menuLevel") == 1){//레벨이 1이면 menu_grp_code에도 동일한 값을 넣어줘야한다.
                    map.put("menuGrpCode", map.get("menuId"));
                    generalMapper.insert("MEN", "updateGrpCodeMenuTemp",map );
                }

            } else if ("U".equals(map.get("rowStatus"))) {
                resultCnt += generalMapper.insert("MEN", "updateMenuTemp",map );
            } else if ("D".equals(map.get("rowStatus"))) {
                resultCnt += generalMapper.insert("MEN", "deleteMenuTemp", map);
            }
        }

        return resultCnt > 0;
    }
}
