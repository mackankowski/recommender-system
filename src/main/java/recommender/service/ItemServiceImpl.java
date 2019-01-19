package recommender.service;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.stereotype.Service;
import recommender.model.Item;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


@Service("itemService")
public class ItemServiceImpl implements ItemService {

    private static int recommendedItemsCount = 5;
    private static String fileCsv = "table.csv";
    private static String popularCsv = "popular.csv";

    @Override
    public List<Item> fillItemsItemBased(long userId) {
        List<Item> items = new ArrayList<>();
        try {
            //ClassLoader classLoader = getClass().getClassLoader();
            //File file = new File(classLoader.getResource("table.csv").getFile());
            File file = new File(fileCsv);
            DataModel model = new FileDataModel(new File(file.getAbsolutePath()));
            ItemSimilarity itemSimilarity = new EuclideanDistanceSimilarity(model);
            Recommender itemRecommender = new GenericItemBasedRecommender(model,itemSimilarity);
            List<RecommendedItem> itemRecommendations = itemRecommender.recommend(userId, recommendedItemsCount);
            for (RecommendedItem item : itemRecommendations) {
                items.add(new Item(item.getItemID(), item.getValue()));
            }
        } catch (Exception e)
        {
            items.clear();
            System.out.println(e);
        }
        return items;
    }

    @Override
    public List<Item> fillItemsUserBased(long userId) {
        List<Item> items = new ArrayList<>();
        try {
                //ClassLoader classLoader = getClass().getClassLoader();
                //File file = new File(classLoader.getResource("table.csv").getFile());
                File file = new File(fileCsv);
                DataModel model = new FileDataModel(new File(file.getAbsolutePath()));
                UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
                UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
                UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
                List<RecommendedItem> recommendations = recommender.recommend(userId, recommendedItemsCount);
                for (RecommendedItem item : recommendations) {
                    items.add(new Item(item.getItemID(), item.getValue()));
                }
            } catch (Exception e)
            {
                items.clear();
                System.out.println(e);
            }
        return items;
    }

    @Override
    public List<Integer> popularItems() {
        List<Integer> items = new ArrayList<>();
        BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(popularCsv));
			String line = reader.readLine();
			while (line != null) {
				System.out.println(line);
				items.add(Integer.parseInt(line));
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return items;
    }

}
