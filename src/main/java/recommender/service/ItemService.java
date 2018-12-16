package recommender.service;

import recommender.model.Item;
import java.util.List;

public interface ItemService {
    List<Item> fillItemsUserBased(long userId);
    //TO DELETE ??
    List<Item> findAllItems(long userId);
    List<Item> fillItemsItemBased(long userId);
}
