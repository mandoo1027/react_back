package com.commerce.service.MNU.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class MNU0301S01R {
	private List<MNUMenu> ADM;
	private List<MNUMenu> admMappingList;
}
