package com.symbio.bigdata.service.impl;

import com.symbio.bigdata.dto.ManagerDto;
import com.symbio.bigdata.dto.StatsDto;
import com.symbio.bigdata.enums.PeriodType;
import com.symbio.bigdata.query.condition.StatsCondition;
import com.symbio.bigdata.repo.ManagerRepo;
import com.symbio.bigdata.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.symbio.bigdata.constant.RolePriority.ROLES_PRIORITY;

@Service
public class ManagerServiceImpl implements ManagerService {
	@Autowired
	private ManagerRepo managerRepo;
	
	@Override
	public ManagerDto findByHrId(String siteName, String statsHrId) {
		List<ManagerDto> managers = managerRepo.findByHrId(siteName,statsHrId);
		//当managers存在时排序且获取级别排首位的最大值
		if (managers != null) {
			Optional<ManagerDto> opManager = managers.stream().sorted((m1, m2) -> {
				return ROLES_PRIORITY.get(m2.getLevel().toUpperCase())
						.compareTo(ROLES_PRIORITY.get(m1.getLevel().toUpperCase()));
			}).findFirst();
			return opManager.isPresent() ? opManager.get() : null;
		} else {
			return null;
		}
	}

	@Override
	public List<ManagerDto> findL4ByHrId(String hrId) {
		List<ManagerDto> managers = managerRepo.selectL4ByHrId(hrId);
		return managers;

	}

	@Override
	public List<ManagerDto> findChildren(String siteName,String statsHrId,String level) {
		List<ManagerDto> managers = managerRepo.findChildren(siteName,statsHrId,level);
		return managers;
	}

	@Override
	public HashMap<String,Object> findHasL2(String siteName,String hrId,String level){
		List<ManagerDto> managers = managerRepo.findHasL2(siteName,hrId,level);
		HashMap<String,Object> hashMap = new HashMap<>();
		switch (level) {
			case "AGENT":
				if(managers != null && managers.size()>0){
					ManagerDto  managerDto = managers.get(0);//AGENT的父L1的信息
				if(managerDto.getParentId().equals(siteName)){ //判断父L1是否有父L2
					//获取level=Agent的hr_id,name,parent_id,level
					List<ManagerDto> managers1 = managerRepo.getCurData(hrId,"AGENT");
					ManagerDto agent = managers1.get(0);
					//传入用户level=agent的parent_id和L1，返回父L1的hr_id,name,parent_id,level
					List<ManagerDto> managers2 = managerRepo.getCurData(agent.getParentId(),"L1");
					List<ManagerDto> managerDtoList = new ArrayList<>();
					managerDtoList.add(managers1.get(0));
					managerDtoList.add(managers2.get(0));
					hashMap.put("msg","OK");
					hashMap.put("code","200");
					hashMap.put("flag","false");
					hashMap.put("data",managerDtoList);
				}else{ //当父L1有L2时
					List<ManagerDto> managers1 = managerRepo.getCurData(hrId,"AGENT");
					ManagerDto agent = managers1.get(0);
					List<ManagerDto> managers2 = managerRepo.getCurData(agent.getParentId(),"L1");
					String L1ParentId = managers2.get(0).getParentId();
					List<ManagerDto> managers3 = managerRepo.getCurData(L1ParentId,"L2");
					List<ManagerDto> managerDtoList = new ArrayList<>();
					managerDtoList.add(managers1.get(0));
					managerDtoList.add(managers2.get(0));
					managerDtoList.add(managers3.get(0));
					hashMap.put("msg","OK");
					hashMap.put("code","200");
					hashMap.put("flag","true");
					hashMap.put("data",managerDtoList);
				}}
				break;
			case "L1":
				if(managers != null && managers.size()>0){
					ManagerDto  managerDto = managers.get(0);
                if(managerDto.getParentId().equals(siteName)){ //当自己没有父L2时
					List<ManagerDto> managers1 = managerRepo.getCurData(hrId,"L1");
					hashMap.put("msg","OK");
					hashMap.put("code","200");
					hashMap.put("flag","false");
					hashMap.put("data",managers1);
				}else{ // 当有父L2时
					List<ManagerDto> managers1 = managerRepo.getCurData(hrId,"L1");
					ManagerDto  managerDto1 = managers1.get(0);
					List<ManagerDto> managers2 = managerRepo.getCurData(managerDto1.getParentId(),"L2");
					List<ManagerDto> managerDtoList = new ArrayList<>();
					managerDtoList.add(managers1.get(0));
					managerDtoList.add(managers2.get(0));
					hashMap.put("msg","OK");
					hashMap.put("code","200");
					hashMap.put("flag","true");
					hashMap.put("data",managerDtoList);
				}}
				break;
			case "L3":
                if(managers != null && managers.size()>0){
                	boolean L2Flag = false;
                	for (ManagerDto dto: managers){
						String temp_level = dto.getLevel();
						if("L2".equals(temp_level)){
							L2Flag = true;
							break;
						}

					}
                	hashMap.put("msg","OK");
					hashMap.put("code","200");
					hashMap.put("flag",L2Flag);
					hashMap.put("data","");
				}
				break;
			default:
				hashMap.put("msg","OK");
				hashMap.put("code","200");
				hashMap.put("flag","false");
				hashMap.put("data","");
				break;
		}
		return hashMap;
	}

	@Override
	public List<StatsDto> findChildrenData(String lobName, String statsHrId, PeriodType periodType, StatsCondition statsCondition){
		List<StatsDto> managers = managerRepo.findChildrenData(lobName,statsHrId,periodType,statsCondition);
		return managers;
	}

	@Override
	public List<StatsDto> findL4ChildrenData(String hrId, PeriodType periodType, StatsCondition condition){
		List<StatsDto> managers = managerRepo.selectL4ChildrenData(hrId,periodType,condition);
		return managers;
	}

	@Override
	public List<ManagerDto> findL4Children(String siteName,String statsHrId,String level) {
		return managerRepo.selectL4Children(siteName,statsHrId,level);
	}

}
