package MyAdsBoard;

import MyAdsBoard.entity.*;

import java.util.List;

public interface MyAdsBoardService {
    //Ad
    Long countAds();
    void addAd(Ad ad);
    void updateAd(Ad ad);
    void deleteAd(Long id);
    void deleteAd(Ad ad);
    void deleteAds(UserProfile userProfile);
    void deleteAds(Long[] ids);
    Ad getAdById(Long id);
    List<Ad> getAllAds();
    List<Ad> getAdsByRubric(String rubric);
    List<Ad> getAdsByTemplate(String template);
    List<Ad> getAdsByUser(UserProfile userProfile);
    List<Ad> getAdsByAddressTemplate(String template);
    List<Ad> getAdsByTitleAndAddressTemplate(String adTitleTemplate, String addressTemplate);

    //Category
    void addCategory (Category category);
    void updateCategory (Category category);
    void deleteCategory (Long id);
    List<Category> getAllCategories();
    Category getCategory(Long id);

    //Message
    void addMessage(Message message);
    void deleteMessage(Message message);
    void deleteMessage(Long id);
    void deleteMessage(Ad ad);
    void deleteMessages(Long[] ids);
    Message getMessageById(Long id);
    List<Message> getAllMessages();
    List<Message> getMessagesByAd(Ad ad);
    List<Message> getInMessagesByUser(String email);
    List<Message> getOutMessagesByUser(String email);

    //Photo
    void addPhoto(Photo photo);
    void updatePhoto(Photo photo);
    void deletePhoto(Photo photo);
    void deletePhoto(Ad ad);
    Photo getPhotoById(Long id);
    List<Photo> getPhotosByAd(Ad ad);

    //UserProfile
    void addUser(UserProfile userProfile);
    void updateUser(UserProfile userProfile);
    void deleteUser(Long id);
    void deleteUser(UserProfile userProfile);
    UserProfile getUser(Long id);
    UserProfile getUser(String email);
    List<UserProfile> getAllUsers();
}
