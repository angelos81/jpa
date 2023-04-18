package com.jpa.member.model.mapper;

import com.jpa.member.entity.Member;
import com.jpa.member.model.MemberModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface MemberMapper {
    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    /**
     * Entity -> Model 변환
     * @param member
     * @return MemberModel
     */
    @Mapping(target = "id",    source = "member.id")    // 클래스.필드명 접근(허용)
    @Mapping(target = "name",  source = "name")         // 필드명 직접 접근(허용)
    @Mapping(target = "email", source = "email")
    MemberModel entityToModel(Member member);
}
