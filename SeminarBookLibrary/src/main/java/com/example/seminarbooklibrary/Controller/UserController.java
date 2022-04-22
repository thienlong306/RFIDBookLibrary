package com.example.seminarbooklibrary.Controller;

import com.example.seminarbooklibrary.Domain.BookDomain;
import com.example.seminarbooklibrary.Domain.BorrowDetailDomain;
import com.example.seminarbooklibrary.Domain.BorrowDomain;
import com.example.seminarbooklibrary.Domain.UserDomain;
import com.example.seminarbooklibrary.Model.UserModel;
import com.example.seminarbooklibrary.Repository.BookRepository;
import com.example.seminarbooklibrary.Repository.UserRepository;
import com.example.seminarbooklibrary.Service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

@Controller
@RequestMapping("")
public class UserController {
    @Autowired
    BookService bookService;
    @Autowired
    BorrowService borrowService;
    @Autowired
    BorrowDetailService borrowDetailService;
    @Autowired
    StorageService storageService;
    @Autowired
    UserService userService;
    @Autowired
    HttpSession httpSession;
    @GetMapping("")
    public ModelAndView index(){
        ModelAndView modelAndView=new ModelAndView();
        if (httpSession.getAttribute("user")!=null){
            modelAndView.addObject("user",httpSession.getAttribute("user"));
        }
        modelAndView.addObject("books",bookService.findAll());
        modelAndView.addObject("booksBorrow", bookService.findAllById(bookService.getListIdBookByListRFIDTest()));
        modelAndView.setViewName("user/BrowseBook");

        return modelAndView;
    }
    @GetMapping("userBorrow")
    public ModelAndView getUserBorrow(HttpSession httpSession) {
        ModelAndView modelAndView = new ModelAndView();
        if (httpSession.getAttribute("user")!=null){
            modelAndView.addObject("user",httpSession.getAttribute("user"));
        }
        modelAndView.addObject("booksBorrow", bookService.findAllById(bookService.getListIdBookByListRFIDTest()));
        UserDomain userDomain= (UserDomain) httpSession.getAttribute("user");
        if (userDomain!=null){
            modelAndView.addObject("listInfoBorrow",borrowService.getListUserBorrowModel(userDomain.getIdUser()));
        }else
            modelAndView.addObject("listInfoBorrow",borrowService.getListUserBorrowModel(null));
        modelAndView.setViewName("user/UserBorrow");
        return modelAndView;
    }
    @GetMapping("userBorrow/details/{idBorrow}")
    public ModelAndView getDetailsBorrow(@PathVariable("idBorrow") Long idBorrow) {
        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("listInfoBorrow",borrowService.getListBorrowModel());
        if (httpSession.getAttribute("user")!=null){
            modelAndView.addObject("user",httpSession.getAttribute("user"));
        }
        modelAndView.addObject("booksBorrow", bookService.findAllById(bookService.getListIdBookByListRFIDTest()));
        UserDomain userDomain= (UserDomain) httpSession.getAttribute("user");
        ArrayList<Long> listBook=new ArrayList<>();
        for (BorrowDetailDomain borrowDetailDomain:borrowDetailService.findAllByIdBorrow(idBorrow)){
            listBook.add(borrowDetailDomain.getIdBook());
        }
        modelAndView.addObject("userBorrow",userService.getById(borrowService.getById(idBorrow).getIdUser()));
        modelAndView.addObject("borrow",borrowService.getById(idBorrow));
        modelAndView.addObject("books",bookService.findAllById(listBook));
        modelAndView.setViewName("user/UserDetailsBorrow");
        return modelAndView;
    }
    @GetMapping("/data/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename){
        Resource file=storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=\""+file.getFilename()+"\"").body(file);
    }
    @GetMapping("borrow")
    public ModelAndView getScanBorrow() {
        ModelAndView modelAndView = new ModelAndView();
        if (httpSession.getAttribute("user")!=null){
            modelAndView.addObject("user",httpSession.getAttribute("user"));
        }else {
            modelAndView.addObject("user",new UserModel());
        }
        modelAndView.addObject("booksBorrow", bookService.findAllById(bookService.getListIdBookByListRFIDTest()));
        modelAndView.setViewName("user/BorrowBookOnline");
        return modelAndView;
    }
    @GetMapping("addBookBorrow/{idBook}")
    public ModelAndView addBookBorrow(@PathVariable("idBook") Long idBook, HttpServletRequest httpServletRequest){
        ModelAndView modelAndView = new ModelAndView();
        bookService.addBookBorrow(idBook);
        modelAndView.setViewName("redirect:"+httpServletRequest.getHeader("Referer"));
        return modelAndView;
    }
    @GetMapping("deleteBookBorrow/{idBook}")
    public ModelAndView deleteBookBorrow(@PathVariable("idBook") Long idBook){
        ModelAndView modelAndView = new ModelAndView();
        bookService.deleteBookBorrow(idBook);
        modelAndView.setViewName("redirect:/borrow");
        return modelAndView;
    }
    @GetMapping("login")
    public ModelAndView getLogin() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("booksBorrow", bookService.findAllById(bookService.getListIdBookByListRFIDTest()));
        modelAndView.addObject("user", new UserModel());
        modelAndView.setViewName("auth/Login");
        return modelAndView;
    }
    @PostMapping("login")
    public ModelAndView postLogin(@ModelAttribute("user") UserModel userModel) {
        ModelAndView modelAndView = new ModelAndView();
        UserDomain userDomain=userService.findByLoginNameUserAndLoginPasswordUser(userModel.getLoginNameUser(),userModel.getLoginPasswordUser());
        if(userDomain!=null){
            httpSession.setAttribute("user",userDomain);
            modelAndView.addObject("booksBorrow", bookService.findAllById(bookService.getListIdBookByListRFIDTest()));
            modelAndView.setViewName("redirect:/");
        }else
        {
            modelAndView.addObject("booksBorrow", bookService.findAllById(bookService.getListIdBookByListRFIDTest()));
            modelAndView.addObject("user", new UserModel());
            modelAndView.setViewName("redirect:/login");
        }

        return modelAndView;
    }
    @GetMapping("signUp")
    public ModelAndView getSignUp() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("booksBorrow", bookService.findAllById(bookService.getListIdBookByListRFIDTest()));
        modelAndView.addObject("user", new UserModel());
        modelAndView.setViewName("auth/SignUp");
        return modelAndView;
    }
    @PostMapping("signUp")
    public ModelAndView postSignUp(@ModelAttribute("user") UserModel userModel) {
        ModelAndView modelAndView = new ModelAndView();
        UserDomain userDomain=new UserDomain();
        BeanUtils.copyProperties(userModel,userDomain);
        System.out.println(userModel.toString());
        userService.saveAndFlush(userDomain);
        modelAndView.addObject("booksBorrow", bookService.findAllById(bookService.getListIdBookByListRFIDTest()));
        modelAndView.addObject("user", new UserModel());
        modelAndView.setViewName("redirect:/login");
        return modelAndView;
    }
    @GetMapping("logout")
    public ModelAndView getLogout() {
        ModelAndView modelAndView = new ModelAndView();
        httpSession.removeAttribute("user");
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
    @PostMapping("submitBorrow")
    public ModelAndView postSubmitBorrow(@ModelAttribute("user") UserModel userModel) {
        ModelAndView modelAndView = new ModelAndView();
        BorrowDomain borrowDomain=new BorrowDomain();
        UserDomain userDomain=(UserDomain) httpSession.getAttribute("user");
        LocalDate localDate = LocalDate.now();
        borrowDomain.setBeginDateBorrow(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        borrowDomain.setEndDateBorrow(Date.from((localDate.plusDays(4)).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        borrowDomain.setIdUser(userDomain.getIdUser());
        borrowService.saveAndFlush(borrowDomain);
//        System.out.println(borrowService.findTopByOrderByIdBorrowDesc().getIdBorrow());
        for (Long id:bookService.getListIdBook()){
            BookDomain bookDomain=bookService.getById(id);
            bookDomain.setStatusBook(0);
            bookService.saveAndFlush(bookDomain);
            BorrowDetailDomain borrowDetailDomain=new BorrowDetailDomain();
            borrowDetailDomain.setIdBook(id);
            borrowDetailDomain.setIdBorrow(borrowService.findTopByOrderByIdBorrowDesc().getIdBorrow());
            borrowDetailService.saveAndFlush(borrowDetailDomain);
        }
        bookService.clearListIdBook();
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
    @GetMapping("search")
        public ModelAndView searchBook(@RequestParam(value = "contentSearch",required = false) String contentSearch,@RequestParam(value = "menuSearch",required = false) String menuSearch){
        ModelAndView modelAndView=new ModelAndView();
        if(StringUtils.hasText(contentSearch)){
            if (menuSearch.equals("All"))
                modelAndView.addObject("books",bookService.findAllByTitleBookOrAuthorBookContaining(contentSearch));
            if (menuSearch.equals("Book"))
                modelAndView.addObject("books",bookService.findByTitleBookContaining(contentSearch));
            if (menuSearch.equals("Author"))
                modelAndView.addObject("books",bookService.findByAuthorBookContaining(contentSearch));
        }else
            modelAndView.addObject("books",bookService.findAll());
        modelAndView.addObject("booksBorrow", bookService.findAllById(bookService.getListIdBookByListRFIDTest()));
        modelAndView.setViewName("user/BrowseBook");
        return modelAndView;
    }
}
