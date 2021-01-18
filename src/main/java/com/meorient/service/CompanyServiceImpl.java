package com.meorient.service;

import com.meorient.mapper.CompanyMapper;
import com.meorient.pojo.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompanyServiceImpl implements CompanyService {
    Map<String, List<Company>> dataInfo;

    @Autowired
    CompanyMapper companyMapper;

    public Map<String, List<Company>> getAllCompanyMsgSplit() {
        dataInfo = new HashMap<>();
        Map<String, Integer> map = new HashMap<>();
        List<Company> allMsg = null;
        int start = 0, end = 1000;
        int count = 0, temp = 0;

        do {
            temp = count;
            map.put("start", start);
            map.put("end", end);

            allMsg = companyMapper.getAllMsgByMap(map);

            for (Company company : allMsg) {
                count++;
                if (company.getFullName() == null) {
                    continue;
                }
                // 存放所有相同ID公司
                List<Company> list;
                if (!dataInfo.containsKey(company.getTranId())) {
                    list = new ArrayList<>();
                    list.add(company);
                    dataInfo.put(company.getTranId(), list);
                } else {
                    list = dataInfo.get(company.getTranId());
                    list.add(company);
                    dataInfo.put(company.getTranId(), list);
                }
            }
            start += 1000;
            end += 1000;
        } while (count != temp);
        return dataInfo;
    }

    public Map<String, List<Company>> getAllMsg() {
        dataInfo = new HashMap<>();
        Map<String, Integer> map = new HashMap<>();
        List<Company> allMsg = null;

        allMsg = companyMapper.getAllCompanyMsg();

        for (Company company : allMsg) {
            if (company.getFullName() == null) {
                continue;
            }
            // 存放所有相同ID公司
            List<Company> list;
            if (!dataInfo.containsKey(company.getTranId())) {
                list = new ArrayList<>();
                list.add(company);
                dataInfo.put(company.getTranId(), list);
            } else {
                list = dataInfo.get(company.getTranId());
                list.add(company);
                dataInfo.put(company.getTranId(), list);
            }
        }

        return dataInfo;
    }


    @Override
    public List<Company> getAllMsgByMap(Map<String, Integer> map) {
        return null;
    }
    @Override
    public List<Company> getAllCompanyMsg() {
        return null;
    }
}
