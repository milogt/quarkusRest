package edu.uchicago.tiang.services;

import edu.uchicago.tiang.models.FavoriteItem;
import edu.uchicago.tiang.repos.FavsRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.NotSupportedException;
import java.util.List;

@ApplicationScoped
public class FavsService {

    @Inject
    FavsRepository favsRepository;

    public FavoriteItem add(FavoriteItem favoriteItem){
        FavoriteItem checkDup = favsRepository.get(favoriteItem.getId());
        if (null != checkDup){
            throw new NotSupportedException("The FavoriteItem with id " + favoriteItem.getId() + " already exists");
        }
        return favsRepository.add(favoriteItem);
    }

    public FavoriteItem get(String id){
        FavoriteItem item = favsRepository.get(id);
        if (null == item){
            throw new NotFoundException("The FavoriteItem with id " + id + " was not found");
        }
        return favsRepository.get(id);
    }

    public List<FavoriteItem> getAll(){
        List<FavoriteItem> items = favsRepository.getAll();
        if (null == items){
            throw new NotFoundException("No FavoriteItem is found");
        }
        return favsRepository.getAll();
    }

    public FavoriteItem delete(String id){
        FavoriteItem item = favsRepository.get(id);
        if (null == item){
            throw new NotFoundException("The FavoriteItem with id " + id + " was not found");
        }
        return favsRepository.delete(id);
    }

    public List<FavoriteItem> paged(int page){
        return favsRepository.paged(page);
    }

    public FavoriteItem update(FavoriteItem newItem, String id){
        FavoriteItem item = favsRepository.get(id);
        if (null == item){
            throw new NotFoundException("The FavoriteItem with id " + id + " was not found");
        }
        return favsRepository.update(newItem, id);
    }
}
