package MyAdsBoard;

import MyAdsBoard.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class MyAdsBoardController {
    @Autowired
    private MyAdsBoardService myAdsBoardService;
    @Autowired
    private ShaPasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @RequestMapping("/")
    public String index(Model model){
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!object.equals("anonymousUser")) {
            User user = (User) object;
            UserProfile userProfile = myAdsBoardService.getUser(user.getUsername());
            model.addAttribute("userId", userProfile.getId());
            model.addAttribute("userName", userProfile.getName());
            if (userProfile.getUserRole().equals(UserRole.ADMIN))
                model.addAttribute("admin", true);
            else
                model.addAttribute("user", true);
        }
        model.addAttribute("numOfAds", myAdsBoardService.countAds());
        model.addAttribute("categories", myAdsBoardService.getAllCategories());
        model.addAttribute("adList", myAdsBoardService.getAllAds());
        model.addAttribute("rubricDefVal", "default");
        model.addAttribute("rubricDefName", "Оберіть категорію");
        return "index";
    }

    @RequestMapping(value = "/myAds")
    public String myAds(Model model) {
        UserProfile userProfile = getCurrentUserProfile();
        model.addAttribute("userId", userProfile.getId());
        model.addAttribute("userName", userProfile.getName());
        model.addAttribute("adList", userProfile.getAds());
        return "myAds";
    }

    @RequestMapping(value = "/search")
    public String search(Model model, @RequestParam(required = false) String rubric, @RequestParam(required = false) String adTitleTemplate,
                         @RequestParam(required = false) String addressTemplate) {
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        StringBuilder patternQueryLike = new StringBuilder("%%");
        if (!object.equals("anonymousUser")) {
            User user = (User) object;
            UserProfile userProfile = myAdsBoardService.getUser(user.getUsername());
            model.addAttribute("userId", userProfile.getId());
            model.addAttribute("userName", userProfile.getName());
            if (userProfile.getUserRole().equals(UserRole.ADMIN))
                model.addAttribute("admin", true);
            else
                model.addAttribute("user", true);
        }
        model.addAttribute("numOfAds", myAdsBoardService.countAds());
        model.addAttribute("categories", myAdsBoardService.getAllCategories());
        if (rubric.equals("default")) {
            model.addAttribute("rubricDefVal", "default");
            model.addAttribute("rubricDefName", "Оберіть категорію");
        }
        else {
            model.addAttribute("rubricDefVal", rubric);
            model.addAttribute("rubricDefName", rubric);
            patternQueryLike.insert(1, rubric);
            model.addAttribute("adList", myAdsBoardService.getAdsByRubric(patternQueryLike.toString()));
            return "index";
        }
        if (adTitleTemplate != null) {
            patternQueryLike.insert(1, adTitleTemplate);
            model.addAttribute("adList", myAdsBoardService.getAdsByTemplate(patternQueryLike.toString()));
        }
        else if (addressTemplate != null) {
            patternQueryLike.insert(1, addressTemplate);
            model.addAttribute("adList", myAdsBoardService.getAdsByAddressTemplate(patternQueryLike.toString()));
        }
        else if (adTitleTemplate != null && addressTemplate != null) {
            adTitleTemplate = patternQueryLike.insert(1, adTitleTemplate).toString();
            patternQueryLike.delete(1, patternQueryLike.length() - 1);
            addressTemplate = patternQueryLike.insert(1, addressTemplate).toString();
            model.addAttribute("adList", myAdsBoardService.getAdsByTitleAndAddressTemplate(adTitleTemplate, addressTemplate));
        }
        else
            model.addAttribute("adList", myAdsBoardService.getAllAds());
        return "index";
    }

    @RequestMapping(value = "/registrationForm")
    public String registrationForm(Model model, @RequestParam(required = false) String error,
                                   @RequestParam(required = false) String email,@RequestParam(required = false) String name,
                                   @RequestParam(required = false) String phone, @RequestParam(required = false) String state,
                                   @RequestParam(required = false) String city,@RequestParam(required = false) String street) {
        if (error != null) {
            model.addAttribute("email", email);
            model.addAttribute("name", name);
            if (phone != null)
                model.addAttribute("phone", phone);
            model.addAttribute("state", state);
            model.addAttribute("city", city);
            if (street != null)
                model.addAttribute("street", street);
            switch (error) {
                case "email":
                    model.addAttribute("errorText", "Цей E-Mail вже зареєстрований у системі!");
                    break;
                case "pswd":
                    model.addAttribute("errorText", "Паролі не збігаються!");
                    break;
            }
        }
        return "registrationForm";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@RequestParam String email, @RequestParam String password, @RequestParam String passwordConfirm,
                               @RequestParam String name, @RequestParam(required = false) String phone, @RequestParam String state,
                               @RequestParam String city, @RequestParam(required = false) String street) {
        UserProfile userProfile = myAdsBoardService.getUser(email);
        if (userProfile == null && password.equals(passwordConfirm)) {
            String encPass = passwordEncoder.encodePassword(password, null);
            userProfile = new UserProfile(email, encPass, name, phone, new Address(state, city, street), UserRole.USER);
            myAdsBoardService.addUser(userProfile);
            userDetailsService.loadUserByUsername(email);
            return "redirect:/login?registrationSuccessful";
        } else {
            StringBuilder stringBuilder = new StringBuilder()
                    .append("email=").append(email)
                    .append("&name=").append(name)
                    .append("&phone=").append(phone)
                    .append("&state=").append(state)
                    .append("&city=").append(city);
            if (street != null)
                stringBuilder.append("&street").append(street);
            String userParams = stringBuilder.toString();
            if (userProfile != null)
                return "redirect:/registrationForm?error=email&" + userParams;
            else
                return "redirect:/registrationForm?error=pswd&"  + userParams;
        }
    }

    @RequestMapping("/admin")
    public String admin(Model model){
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserProfile userProfile = myAdsBoardService.getUser(user.getUsername());
        model.addAttribute("adminId", userProfile.getId());
        model.addAttribute("adminName", userProfile.getName());
        model.addAttribute("categories", myAdsBoardService.getAllCategories());
        model.addAttribute("users", myAdsBoardService.getAllUsers());
        model.addAttribute("ads", myAdsBoardService.getAllAds());
        return "admin";
    }

    @RequestMapping(value = "/changeUserProfileForm")
    public String changeUserProfileForm(Model model, @RequestParam Long id, @RequestParam(required = false) String error,
                                        @RequestParam(required = false) String change) {
        UserProfile currentUserProfile = getCurrentUserProfile();
        UserProfile editUserProfile = myAdsBoardService.getUser(id);
        if (!id.equals(currentUserProfile.getId()) && !currentUserProfile.getUserRole().equals(UserRole.ADMIN))
            return "unauthorized";
        if (id.equals(currentUserProfile.getId()))
            model.addAttribute("myProfile", true);
        if (currentUserProfile.getUserRole().equals(UserRole.ADMIN)) {
            model.addAttribute("admin", true);
            if (editUserProfile.getUserRole().equals(UserRole.ADMIN)) {
                model.addAttribute("userRole0", "ADMIN");
                model.addAttribute("userRole1", "USER");
            } else {
                model.addAttribute("userRole0", "USER");
                model.addAttribute("userRole1", "ADMIN");
            }
        }
        model.addAttribute("id", id);
        model.addAttribute("email", editUserProfile.getEmail());
        model.addAttribute("name", editUserProfile.getName());
        model.addAttribute("phone", editUserProfile.getPhone());
        Address address = editUserProfile.getAddress();
        model.addAttribute("state", address.getState());
        model.addAttribute("city", address.getCity());
        if (error != null && error.equals("pswd"))
            model.addAttribute("errorText", "Паролі не збігаються!");
        if (change != null && change.equals("successfully"))
            model.addAttribute("successfully", "Зміни збережені успішно!");
        return "changeUserProfileForm";
    }

    @RequestMapping(value = "/changeUserProfile")
    public String changeUserProfile(@RequestParam Long id, @RequestParam String email, @RequestParam(required = false) String password,
                                    @RequestParam(required = false) String passwordConfirm, @RequestParam String name,
                                    @RequestParam(required = false) String phone, @RequestParam String state,
                                    @RequestParam String city, @RequestParam(required = false) String street,
                                    @RequestParam(required = false) String userRole){
        UserProfile currentUserProfile = getCurrentUserProfile();
        if (!id.equals(currentUserProfile.getId()) && !currentUserProfile.getUserRole().equals(UserRole.ADMIN))
            return "unauthorized";
        else {
            UserProfile editUserProfile = myAdsBoardService.getUser(id);
            editUserProfile.setEmail(email);
            if (password != null && passwordConfirm != null)
                if (password.equals(passwordConfirm))
                    editUserProfile.setPassword(passwordEncoder.encodePassword(password, null));
                else
                    return "redirect:/changeUserProfileForm?error=pswd";
            editUserProfile.setName(name);
            if (phone != null)
                editUserProfile.setPhone(phone);
            Address address = new Address(state, city, street);
            if (!address.equals(editUserProfile.getAddress()))
                editUserProfile.setAddress(address);
            if (userRole != null)
                if (userRole.equals("ADMIN"))
                    editUserProfile.setUserRole(UserRole.ADMIN);
                else
                    editUserProfile.setUserRole(UserRole.USER);
            myAdsBoardService.updateUser(editUserProfile);
            return "redirect:/changeUserProfileForm?change=successfully&id=" + id;
        }
    }

    @RequestMapping(value = "/deleteUserProfile")
    public String deleteUserProfile(@RequestParam Long id){
        UserProfile currentUserProfile = getCurrentUserProfile();
        if (!id.equals(currentUserProfile.getId()) && !currentUserProfile.getUserRole().equals(UserRole.ADMIN))
            return "unauthorized";
        else
            myAdsBoardService.deleteUser(id);
        if (id.equals(currentUserProfile.getId()))
            return "redirect:/logout";
        else if (currentUserProfile.getUserRole().equals(UserRole.ADMIN))
            return "redirect:/admin";
        else
            return "";
    }

    @RequestMapping(value = "/category")
    public String addCategoryForm(Model model, @RequestParam(required = false) Long id) {
        if (id != null)
            model.addAttribute("category", myAdsBoardService.getCategory(id));
        return "category";
    }

    @RequestMapping(value = "/updateCategory", method = RequestMethod.POST)
    public String addCategory(@RequestParam(required = false) Long id, @RequestParam String name, @RequestParam(required = false) String rubricList) {
        Category category;
        String[] rubrics = rubricList.trim().split("; ");
        if (id != null) {
            category = myAdsBoardService.getCategory(id);
            category.setName(name.trim());
            category.updateRubrics(rubrics);
            myAdsBoardService.updateCategory(category);
        }
        else {
            category = new Category(name.trim(), Arrays.asList(rubrics));
            myAdsBoardService.addCategory(category);
        }
        return "redirect:/admin";
    }

    @RequestMapping(value = "/deleteCategory", method = RequestMethod.POST)
    public String deleteCategory(@RequestParam Long id) {
        myAdsBoardService.deleteCategory(id);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/postNewAd", method = RequestMethod.GET)
    public String postNewAd(Model model, @RequestParam(required = false) String error, @RequestParam(required = false) String title,
                            @RequestParam(required = false) String price, @RequestParam(required = false) String description) {
        UserProfile userProfile = getCurrentUserProfile();
        model.addAttribute("categories", myAdsBoardService.getAllCategories());
        model.addAttribute("userId", userProfile.getId());
        model.addAttribute("userEmail", userProfile.getEmail());
        model.addAttribute("userName", userProfile.getName());
        model.addAttribute("userPhone", userProfile.getPhone());
        model.addAttribute("userAddress", userProfile.getAddress());
        if (error != null) {
            switch (error) {
                case "":
                    break;
                case "title":
                    model.addAttribute("errorText", "Заголовок має містити не більше 70 символів!");
                    break;
                case "rubric":
                    model.addAttribute("errorText", "Необхідно обрати рубрику!");
                    break;
                case "price":
                    model.addAttribute("errorText", "Ціна містить не допустимі символи!");
                    break;
                case "state":
                    model.addAttribute("errorText", "Необхідно обрати стан товару!");
                    break;
                case "seller":
                    model.addAttribute("errorText", "Необхідно обрати тип продавця!");
                    break;
                case "description":
                    model.addAttribute("errorText", "Опис не повинен перевищувати 4096 символів!");
                    break;
            }
            model.addAttribute("title", title);
            model.addAttribute("price", price);
            model.addAttribute("description", description);
        }
        return "postNewAd";
    }

    @RequestMapping(value = "/addAd", method = RequestMethod.POST)
    public String addAd(@RequestParam String title, @RequestParam String rubric, @RequestParam String price,
                        @RequestParam String stateType, @RequestParam String sellerType, @RequestParam String description,
                        @RequestParam(required = false) MultipartFile photoFile1, @RequestParam(required = false) MultipartFile photoFile2,
                        @RequestParam(required = false) MultipartFile photoFile3) {
        String error = checkAdParams(true, "redirect:/postNewAd", title, rubric, price, stateType, sellerType, description);
        if (!error.isEmpty())
            return error;
        UserProfile userProfile = getCurrentUserProfile();
        State state = null;
        Seller seller = null;
        if (stateType.equals("USED"))
            state = State.USED;
        else if (stateType.equals("NEW"))
            state = State.NEW;
        if (sellerType.equals("OWNER"))
            seller = Seller.OWNER;
        else if (sellerType.equals("ENTITY"))
            seller = Seller.ENTITY;
        String address = userProfile.getAddress().toString();
        Ad ad = new Ad(title, rubric, price, state, seller, description, userProfile, address);
        List<Photo> photos = new ArrayList<>();
        if (photoFile1.getSize() > 0 && photoFile1.getSize() <= 1048576)
            try {
                photos.add(new Photo(photoFile1.getBytes(), ad));
            } catch (IOException e) {
                e.printStackTrace();
            }
        if (photoFile2.getSize() > 0 && photoFile2.getSize() <= 1048576)
            try {
                photos.add(new Photo(photoFile2.getBytes(), ad));
            } catch (IOException e) {
                e.printStackTrace();
            }
        if (photoFile3.getSize() > 0 && photoFile3.getSize() <= 1048576)
            try {
                photos.add(new Photo(photoFile3.getBytes(), ad));
            } catch (IOException e) {
                e.printStackTrace();
            }
        ad.setPhotos(photos);
        myAdsBoardService.addAd(ad);
        userProfile.addAd(ad);
        myAdsBoardService.updateUser(userProfile);
        return "redirect:/myAds";
    }

    @RequestMapping(value = "/editAd", method = RequestMethod.GET)
    public String editAd(Model model, @RequestParam Long id, @RequestParam(required = false) String error) {
        UserProfile userProfile = getCurrentUserProfile();
        Ad ad = myAdsBoardService.getAdById(id);
        if (!ad.getUser().equals(userProfile))
            return "redirect:/unauthorized";
        model.addAttribute("categories", myAdsBoardService.getAllCategories());
        model.addAttribute("userId", userProfile.getId());
        model.addAttribute("userEmail", userProfile.getEmail());
        model.addAttribute("userName", userProfile.getName());
        model.addAttribute("userPhone", userProfile.getPhone());
        model.addAttribute("userAddress", userProfile.getAddress());
        model.addAttribute("ad", ad);
        model.addAttribute("rubric", ad.getRubricName());
        model.addAttribute("numOfPhotos", 3 - ad.getPhotos().size());
        if (ad.getState().equals(State.USED)) {
            model.addAttribute("stateNameDef", "Б/в");
            model.addAttribute("state", "NEW");
            model.addAttribute("stateName", "Нові");
        } else {
            model.addAttribute("stateNameDef", "Нові");
            model.addAttribute("state", "USED");
            model.addAttribute("stateName", "Б/в");
        }
        if (ad.getSeller().equals(Seller.OWNER)) {
            model.addAttribute("sellerNameDef", "Власник");
            model.addAttribute("seller", "ENTITY");
            model.addAttribute("sellerName", "Бізнес");
        } else {
            model.addAttribute("sellerNameDef", "Бізнес");
            model.addAttribute("seller", "OWNER");
            model.addAttribute("sellerName", "Власник");
        }
        if (error != null)
            switch (error) {
                case "":
                    break;
                case "title":
                    model.addAttribute("errorText", "Заголовок має містити не більше 70 символів!");
                    break;
                case "rubric":
                    model.addAttribute("errorText", "Необхідно обрати рубрику!");
                    break;
                case "price":
                    model.addAttribute("errorText", "Ціна містить не допустимі символи!");
                    break;
                case "state":
                    model.addAttribute("errorText", "Необхідно обрати стан товару!");
                    break;
                case "seller":
                    model.addAttribute("errorText", "Необхідно обрати тип продавця!");
                    break;
                case "description":
                    model.addAttribute("errorText", "Опис не повинен перевищувати 4096 символів!");
                    break;
            }
            return "editAd";
    }
    @RequestMapping(value = "/updateAd", method = RequestMethod.POST)
    public String updateAd(@RequestParam Long id, @RequestParam String title, @RequestParam String rubric, @RequestParam String price,
                           @RequestParam String stateType, @RequestParam String sellerType, @RequestParam String description,
                           @RequestParam(required = false) MultipartFile photoFile1, @RequestParam(required = false) MultipartFile photoFile2,
                           @RequestParam(required = false) MultipartFile photoFile3) {
        Ad ad = myAdsBoardService.getAdById(id);
        String url = "redirect:/editAd?id=" + ad.getId();
        String error = checkAdParams(false, url, title, rubric, price, stateType, sellerType, description);
        if (!error.isEmpty())
            return error;
        State state = null;
        Seller seller = null;
        if (stateType.equals("USED"))
            state = State.USED;
        else if (stateType.equals("NEW"))
            state = State.NEW;
        if (sellerType.equals("OWNER"))
            seller = Seller.OWNER;
        else if (sellerType.equals("ENTITY"))
            seller = Seller.ENTITY;
        ad.setTitle(title);
        ad.setRubric(rubric);
        ad.setPrice(price);
        ad.setState(state);
        ad.setSeller(seller);
        ad.setDescription(description);
        if (photoFile1 != null && (photoFile1.getSize() > 0 && photoFile1.getSize() <= 1048576)) {
            try {
                Photo photo = new Photo(photoFile1.getBytes(), ad);
                myAdsBoardService.addPhoto(photo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (photoFile2 != null && (photoFile2.getSize() > 0 && photoFile2.getSize() <= 1048576)) {
            try {
                Photo photo = new Photo(photoFile2.getBytes(), ad);
                myAdsBoardService.addPhoto(photo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (photoFile3 != null && (photoFile3.getSize() > 0 && photoFile3.getSize() <= 1048576)) {
            try {
                Photo photo = new Photo(photoFile3.getBytes(), ad);
                myAdsBoardService.addPhoto(photo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        myAdsBoardService.updateAd(ad);
        return url;
    }

    @RequestMapping(value = "/showAd", method = RequestMethod.GET)
    public String showAd(Model model, @RequestParam Long id) {
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!object.equals("anonymousUser")) {
            User user = (User) object;
            UserProfile userProfile = myAdsBoardService.getUser(user.getUsername());
            model.addAttribute("userId", userProfile.getId());
            model.addAttribute("userName", userProfile.getName());
            model.addAttribute("userEmail", userProfile.getEmail());
            if (userProfile.getUserRole().equals(UserRole.ADMIN))
                model.addAttribute("admin", true);
            else
                model.addAttribute("user", true);
        }
        model.addAttribute("numOfAds", myAdsBoardService.countAds());
        model.addAttribute("categories", myAdsBoardService.getAllCategories());
        model.addAttribute("adList", myAdsBoardService.getAllAds());
        model.addAttribute("rubricDefVal", "default");
        model.addAttribute("rubricDefName", "Оберіть категорію");
        Ad ad = myAdsBoardService.getAdById(id);
        model.addAttribute("ad", ad);
        if (ad.getState().equals(State.USED))
            model.addAttribute("stateType", "Б/в");
        else
            model.addAttribute("stateType", "Нові");
        if (ad.getSeller().equals(Seller.OWNER))
            model.addAttribute("sellerType", "Власник");
        else
            model.addAttribute("sellerType", "Бізнес");
        return "showAd";
    }

    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    public String sendMessage(@RequestParam Long adId, @RequestParam String fromUser, @RequestParam String toUser,
                              @RequestParam(required = false) String messageText, @RequestParam(required = false) MultipartFile file,
                              @RequestParam String returnUrl) {
        Ad ad = myAdsBoardService.getAdById(adId);
        String fileName = null;
        byte[] fileBody = null;
        MessageType messageType = null;
        if (file != null && (file.getSize() > 0 && file.getSize() <= 1048576)) {
            try {
                fileName = file.getOriginalFilename();
                fileBody = file.getBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        UserProfile userProfile = getCurrentUserProfile();
        if (userProfile == null || userProfile.getEmail().equals(fromUser))
            messageType = MessageType.IN;
        else if (userProfile != null && userProfile.getEmail().equals(toUser))
            messageType = MessageType.OUT;
        Message message = new Message(fromUser, ad.getUser().getEmail(), messageText, ad, messageType, fileName,fileBody);
        myAdsBoardService.addMessage(message);
            return returnUrl;
    }

    @RequestMapping(value = "/myMessages")
    public String myMessages(Model model, @RequestParam(required = false) Long adId, @RequestParam(required = false) String type) {
        UserProfile userProfile = getCurrentUserProfile();
        String email = userProfile.getEmail();
        model.addAttribute("userId", userProfile.getId());
        model.addAttribute("userName", userProfile.getName());
        if (adId != null) {
            Ad ad = myAdsBoardService.getAdById(adId);
            model.addAttribute("adTitle", ad.getTitle());
            model.addAttribute("messageList", myAdsBoardService.getMessagesByAd(ad));
        } else if (type != null) {
            if (type.equals("in"))
                model.addAttribute("messageList", myAdsBoardService.getInMessagesByUser(email));
            else if (type.equals("out"))
                model.addAttribute("messageList", myAdsBoardService.getOutMessagesByUser(email));
        }
        else
            model.addAttribute("messageList", myAdsBoardService.getAllMessages());
        return "myMessages";
    }

    @RequestMapping(value = "/showMessage")
    public String showMessage(Model model, @RequestParam Long id) {
        Message message = myAdsBoardService.getMessageById(id);
        UserProfile userProfile = getCurrentUserProfile();
        model.addAttribute("userId", userProfile.getId());
        model.addAttribute("userName", userProfile.getName());
        model.addAttribute("userEmail", userProfile.getEmail());
        model.addAttribute("message", message);
        model.addAttribute("adTitle", message.getAd().getTitle());
        if (message.getMessageType().equals(MessageType.IN))
            model.addAttribute("in", true);
        return "showMessage";
    }

    @RequestMapping(value = "/photos")
    public ResponseEntity<byte[]> showPhoto(@RequestParam Long photoId) {
        Photo photo = myAdsBoardService.getPhotoById(photoId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<byte[]>(photo.getPhoto(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/deletePhoto")
    public String deletePhoto(@RequestParam Long photoId) {
        Photo photo = myAdsBoardService.getPhotoById(photoId);
        Ad ad = photo.getAd();
        ad.getPhotos().remove(photo);
        myAdsBoardService.deletePhoto(photo);
        return "redirect:/editAd?id=" + ad.getId();
    }

    @RequestMapping(value = "/deleteAd")
    public String deleteAd(@RequestParam Long id) {
        myAdsBoardService.deleteAd(id);
        return "redirect:/myAds";
    }

    @RequestMapping(value = "/deleteAds", method = RequestMethod.POST)
    public ResponseEntity<Void> deleteAds(@RequestParam(value = "toDelete[]", required = false) Long[] toDelete, Model model) {
        if (toDelete != null)
            myAdsBoardService.deleteAds(toDelete);
        UserProfile userProfile = getCurrentUserProfile();
        model.addAttribute("userId", userProfile.getId());
        model.addAttribute("userName", userProfile.getName());
        model.addAttribute("adList", userProfile.getAds());
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteMessage")
    public String deleteMessage(@RequestParam Long id) {
        myAdsBoardService.deleteMessage(id);
        return "redirect:/myMessages";
    }

    @RequestMapping(value = "/deleteMessages", method = RequestMethod.POST)
    public ResponseEntity<Void> deleteMessages(@RequestParam(value = "toDelete[]", required = false) Long[] toDelete, Model model) {
        if (toDelete != null)
            myAdsBoardService.deleteMessages(toDelete);
        UserProfile userProfile = getCurrentUserProfile();
        model.addAttribute("userName", userProfile.getName());
        model.addAttribute("messageList", myAdsBoardService.getAllMessages());
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/unauthorized")
    public String unauthorized(Model model) {
        model.addAttribute("userName", getCurrentUserProfile().getName());
        return "unauthorized";
    }

    @RequestMapping(value = "/images")
    public ResponseEntity<byte[]> images(@RequestParam String img) {
        String imgPath = new StringBuilder("src\\main\\webapp\\WEB-INF\\pages\\img\\").append(img).toString();
        File imgFile = new File(imgPath);
        HttpHeaders headers = new HttpHeaders();
        if (imgFile.exists() && imgFile.isFile()) {
            byte[] bytes = new byte[(int)imgFile.length()];
            try (FileInputStream fileInputStream = new FileInputStream(imgFile)) {
                fileInputStream.read(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (img.endsWith(".gif"))
                headers.setContentType(MediaType.IMAGE_GIF);
            else if (img.endsWith(".jpg"))
                headers.setContentType(MediaType.IMAGE_JPEG);
            else if (img.endsWith(".png"))
                headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
        }
        else
            return new ResponseEntity<byte[]>(null, headers, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/getAttach")
    public ResponseEntity<byte[]> getAttach(@RequestParam Long messageId, @RequestParam String file) {
        Message message = myAdsBoardService.getMessageById(messageId);
        String fileName = message.getFileName();
        HttpHeaders headers = new HttpHeaders();
        if (fileName != null && fileName.equals(file)) {
            byte[] fileBody = message.getFileBody();
            headers.setContentDispositionFormData("attachment;filename" ,fileName);
            headers.setContentLength(fileBody.length);
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            return new ResponseEntity<byte[]>(fileBody, headers, HttpStatus.OK);
        }
        else
            return new ResponseEntity<byte[]>(null, headers, HttpStatus.BAD_REQUEST);
    }

    private UserProfile getCurrentUserProfile() {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = user.getUsername();
        if (userName.equals("anonymousUser"))
            return null;
        else
            return myAdsBoardService.getUser(userName);
    }

    private String checkAdParams(boolean newAd, String url, String title, String rubric, String price, String sate, String seller, String description) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean error = false;
        if (title.length() > 70) {
            stringBuilder.append("error=title");
            error = true;
        }
        if (rubric.equals("default")) {
            stringBuilder.append("error=rubric");
            error = true;
        }
        for (char c : price.toCharArray())
            if (!Character.isDigit(c)) {
                stringBuilder.append("error=price");
                error = true;
                break;
            }
        if (sate.equals("default")) {
            stringBuilder.append("error=state");
            error = true;
        }
        if (seller.equals("default")) {
            stringBuilder.append("error=seller");
            error = true;
        }
        if (description.length() > 4096) {
            stringBuilder.append("?error=description");
            error = true;
        }
        if (error) {
            if (newAd)
                stringBuilder.insert(0, url + "?");
            else
                stringBuilder.insert(0, url + "&");
            stringBuilder.append("&title=").append(title).append("&price=").append(price).append("&description=").append(description);
        }
        return stringBuilder.toString();
    }
}