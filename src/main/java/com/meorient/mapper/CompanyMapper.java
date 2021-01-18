package com.meorient.mapper;

import com.meorient.pojo.Company;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface CompanyMapper {

    List<Company> getAllMsgByMap(Map<String, Integer> map);

    List<Company> getAllCompanyMsg();

}
