package com.jpa.item.domain.mapper;

import com.jpa.item.domain.entity.Item;
import com.jpa.item.domain.entity.ItemImg;
import com.jpa.item.domain.model.ItemImgModel;
import com.jpa.item.domain.model.ItemListModel;
import com.jpa.item.domain.model.ItemModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface ItemMapper {

    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    /**
     * ItemImg Entity -> ItemImgModel 매핑
     * @param itemImg 
     * @return
     */
    @Mapping(target = "id", source = "id")
    ItemImgModel toItemImgModel(ItemImg itemImg);

    /**
     * Item Entity -> ItemModel 매핑
     * @param item 
     * @return
     */
    @Mapping(target = "itemImgList", ignore = true)
    ItemModel toItemModel(Item item);


    List<ItemListModel> toItemModelList(List<Item> itemList);


}
