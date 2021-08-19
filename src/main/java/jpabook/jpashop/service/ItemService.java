package jpabook.jpashop.service;


import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {


    private final ItemRepository itemRepository;

    @Transactional
    public void save(Item item){
        itemRepository.save(item);
    }


    //변경감지 --> JPA에서 데이터를 수정할 때 변경감지를 귀찮더라도 꼭 써라(merge는 위험하다)
    @Transactional
    public Item updateItem(Long itemId, Book param){
        Item findItem = itemRepository.findOne(itemId);//DB에서 영속성 있는 데이터를 가져옴
        findItem.setPrice(param.getPrice());
        findItem.setName(param.getName());
        findItem.setStockQuantity(param.getStockQuantity());
        return findItem;
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }

}
