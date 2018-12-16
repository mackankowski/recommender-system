package recommender.service;

import recommender.model.Item;
import java.util.List;

public interface ItemService {
    List<Item> fillItems();
    List<Item> findAllItems();
}
