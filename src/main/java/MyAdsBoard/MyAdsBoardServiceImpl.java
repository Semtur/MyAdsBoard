package MyAdsBoard;

import MyAdsBoard.entity.*;
import MyAdsBoard.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyAdsBoardServiceImpl implements MyAdsBoardService {
    @Autowired
    private AdRepository adRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private UserRepository userRepository;

    //Ad
    @Override
    public Long countAds() {
        return adRepository.countAds();
    }
    @Override
    public void addAd(Ad ad) {
        adRepository.save(ad);
    }
    @Override
    public void updateAd(Ad ad) {
        adRepository.save(ad);
    }
    @Override
    public void deleteAd(Long id) {
        adRepository.delete(id);
    }
    @Override
    public void deleteAd(Ad ad) {
        adRepository.delete(ad);
    }
    @Override
    public void deleteAds(UserProfile userProfile) {
        List<Ad> adList = adRepository.findByUser(userProfile);
        adRepository.delete(adList);
    }
    @Override
    public void deleteAds(Long[] ids) {
        for (Long id : ids)
            adRepository.delete(id);
    }
    @Override
    public Ad getAdById(Long id) {
        return adRepository.findOne(id);
    }
    @Override
    public List<Ad> getAllAds() {
        return adRepository.findAll();
    }
    @Override
    public List<Ad> getAdsByRubric(String rubric) {
        return adRepository.findByRubric(rubric);
    }
    @Override
    public List<Ad> getAdsByTemplate(String template) {
        return adRepository.findByTitleTemplate(template);
    }
    @Override
    public List<Ad> getAdsByUser(UserProfile userProfile) {
        return adRepository.findByUser(userProfile);
    }
    @Override
    public List<Ad> getAdsByAddressTemplate(String template) {
        return adRepository.findByAddressTemplate(template);
    }
    @Override
    public List<Ad> getAdsByTitleAndAddressTemplate(String adTitleTemplate, String addressTemplate) {
        return adRepository.findByTitleAndAddressTemplate(adTitleTemplate, addressTemplate);
    }

    //Category
    @Override
    public void addCategory(Category category) {
        categoryRepository.save(category);
    }
    @Override
    public void updateCategory(Category category) {
        categoryRepository.save(category);
    }
    @Override
    public void deleteCategory(Long id) {
        categoryRepository.delete(id);
    }
    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    @Override
    public Category getCategory(Long id) {
        return categoryRepository.findOne(id);
    }

    //Message
    @Override
    public void addMessage(Message message) {
        messageRepository.save(message);
    }
    @Override
    public void deleteMessage(Long id) {
        messageRepository.delete(id);
    }
    @Override
    public void deleteMessage(Message message) {
        messageRepository.delete(message);
    }
    @Override
    public void deleteMessage(Ad ad) {
        List<Message> messageList = messageRepository.findByAd(ad);
        messageRepository.delete(messageList);
    }
    @Override
    public void deleteMessages(Long[] ids) {
        for (Long id : ids)
            messageRepository.delete(id);
    }
    @Override
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
    @Override
    public Message getMessageById(Long id) {
        return messageRepository.findOne(id);
    }
    @Override
    public List<Message> getMessagesByAd(Ad ad) {
        return messageRepository.findByAd(ad);
    }
    @Override
    public List<Message> getInMessagesByUser(String email) {
        return messageRepository.findInMessagesByUser(email);
    }
    @Override
    public List<Message> getOutMessagesByUser(String email) {
        return messageRepository.findOutMessagesByUser(email);
    }

    //Photo
    @Override
    public void addPhoto(Photo photo) {
        photoRepository.save(photo);
    }
    @Override
    public void updatePhoto(Photo photo) {
        photoRepository.save(photo);
    }
    @Override
    public void deletePhoto(Photo photo) {
        photoRepository.delete(photo);
    }
    @Override
    public void deletePhoto(Ad ad) {
        List<Photo> photoList = photoRepository.findByAd(ad);
        photoRepository.delete(photoList);
    }
    @Override
    public Photo getPhotoById(Long id) {
        return photoRepository.findOne(id);
    }
    @Override
    public List<Photo> getPhotosByAd(Ad ad) {
        return photoRepository.findByAd(ad);
    }

    //UserProfile
    @Override
    public void addUser(UserProfile userProfile) {
        userRepository.save(userProfile);
    }
    @Override
    public void updateUser(UserProfile userProfile) {
        userRepository.save(userProfile);
    }
    @Override
    public void deleteUser(Long id) {
        userRepository.delete(id);
    }
    @Override
    public void deleteUser(UserProfile userProfile) {
        userRepository.delete(userProfile);
    }
    @Override
    public UserProfile getUser(Long id) {
        return userRepository.findOne(id);
    }
    @Override
    public UserProfile getUser(String email) {
        return userRepository.findByEmail(email);
    }
    @Override
    public List<UserProfile> getAllUsers() {
        return userRepository.findAll();
    }
}
