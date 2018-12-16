package recommender.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import recommender.model.Item;
import recommender.service.ItemService;

@RestController
public class UserBasedController {
    @Autowired
    ItemService itemService;
    @RequestMapping(value = "/user-based", method = RequestMethod.GET)
    public ResponseEntity<List<Item>> listAllItems() {
        List<Item> recommendedItems = itemService.findAllItems();
        if(recommendedItems.isEmpty()){
            return new ResponseEntity<List<Item>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Item>>(recommendedItems, HttpStatus.OK);
    }
}
