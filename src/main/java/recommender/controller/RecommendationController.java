package recommender.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import recommender.model.Item;
import recommender.service.ItemService;

@RestController
public class RecommendationController {
    @Autowired
    ItemService itemService;

    @RequestMapping(value = "/user-based/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Item>> listAllItemsUserBased(@PathVariable("id") long userId) {
        List<Item> recommendedItems = itemService.fillItemsUserBased(userId);
        if(recommendedItems.isEmpty()){
            return new ResponseEntity<List<Item>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Item>>(recommendedItems, HttpStatus.OK);
    }

    @RequestMapping(value = "/item-based/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Item>> listAllItemsItemBased(@PathVariable("id") long userId) {
        List<Item> recommendedItems = itemService.fillItemsItemBased(userId);
        if(recommendedItems.isEmpty()){
            return new ResponseEntity<List<Item>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Item>>(recommendedItems, HttpStatus.OK);
    }
}