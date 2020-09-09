package com.nongbushim;

import com.nongbushim.Dto.ItemInfoDto;
import com.nongbushim.Enum.ProductRankCode;
import com.nongbushim.Enum.ItemCode;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {
    public List<String> searchAutoCompleteTarget(String term) throws IOException {
        InputStream resource = new ClassPathResource("static/list.txt").getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource));

        return reader.lines().filter(s -> s.contains(term)).collect(Collectors.toList());
    }

    public String searchKindCode(String item) throws IOException {
        InputStream resource = new ClassPathResource("static/listWithKindcode.txt").getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource));

        String line;
        while((line = reader.readLine()) != null){
            if (line.substring(3).equals(item)) break;
        }
        String kindCode = line.substring(0,2);
        return kindCode;
    }

    public ItemInfoDto searchInfo(String input) throws IOException {
        String[] terms = input.split(" ");
        int lastIdx = terms.length-1;
        String itemName = terms[0];
        String kind = terms[1];
        String grade = terms[lastIdx];
        if (lastIdx > 2){
            // input이 3단어보다 긴 경우
            StringBuilder sb = new StringBuilder(kind);
            while (--lastIdx>1) {
                sb.append(" ").append(terms[lastIdx]);
            }
            kind = sb.toString();
        }
        ItemInfoDto itemInfoDto = new ItemInfoDto();
        ItemCode itemCode = ItemCode.searchCode(itemName);
        ProductRankCode rank = ProductRankCode.searchRank(grade);
        itemInfoDto.setItemCode(itemCode.getCode());
        itemInfoDto.setItemCategoryCode(itemCode.getItemCategoryCode().getCode());
        itemInfoDto.setGradeRank(rank.getGradeRank());
        itemInfoDto.setProductRank(rank.getProduceRank());
        itemInfoDto.setKindCode(searchKindCode(itemName + " " + kind));
        return itemInfoDto;
    }
}