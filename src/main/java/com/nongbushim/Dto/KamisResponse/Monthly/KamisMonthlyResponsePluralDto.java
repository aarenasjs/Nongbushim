package com.nongbushim.Dto.KamisResponse.Monthly;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * API결과값이 도,소매값을 포함하고 있을 경우 이 클래스 사용
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KamisMonthlyResponsePluralDto extends KamisMonthlyResponseDto {


    private List<PriceDto> price;
}
