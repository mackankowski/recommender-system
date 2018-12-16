package recommender.service;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.stereotype.Service;
import recommender.model.Item;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service("itemService")
public class ItemServiceImpl implements ItemService {
    private static List<Item> items;
    public List<Item> findAllItems() {
        items = fillItems();
        return items;
    }

    public List<Item> fillItems() {
        List<Item> items = new ArrayList<>();
        try {
                ClassLoader classLoader = getClass().getClassLoader();
                File file = new File(classLoader.getResource("dataset.csv").getFile());
                DataModel model = new FileDataModel(new File(file.getAbsolutePath()));
                UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
                UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
                UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
                List<RecommendedItem> recommendations = recommender.recommend(2, 2);
                for (RecommendedItem item : recommendations) {
                    items.add(new Item(item.getItemID(), item.getValue()));
                }
            } catch (Exception e)
            {
                items.clear();
            }
        return items;
    }
}
