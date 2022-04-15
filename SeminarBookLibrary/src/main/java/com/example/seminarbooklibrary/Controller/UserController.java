package com.example.seminarbooklibrary.Controller;

import com.example.seminarbooklibrary.Domain.BookDomain;
import com.example.seminarbooklibrary.Domain.BorrowDetailDomain;
import com.example.seminarbooklibrary.Domain.BorrowDomain;
import com.example.seminarbooklibrary.Model.UserModel;
import com.example.seminarbooklibrary.Repository.BookRepository;
import com.example.seminarbooklibrary.Service.BookService;
import com.example.seminarbooklibrary.Service.BorrowDetailService;
import com.example.seminarbooklibrary.Service.BorrowService;
import com.example.seminarbooklibrary.Service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.ZoneId;
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
    BookRepository bookRepository;
    @Autowired
    StorageService storageService;
    @GetMapping("")
    public ModelAndView index(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("books",bookService.findAll());
        modelAndView.addObject("booksBorrow", bookService.findAllById(bookService.getListIdBookByListRFIDTest()));
        modelAndView.setViewName("user/BrowseBook");

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
        modelAndView.addObject("booksBorrow", bookService.findAllById(bookService.getListIdBookByListRFIDTest()));
        modelAndView.addObject("user", new UserModel());
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
    @PostMapping("submitBorrow")
    public ModelAndView postSubmitBorrow(@ModelAttribute("user") UserModel userModel) {
        ModelAndView modelAndView = new ModelAndView();

        BorrowDomain borrowDomain=new BorrowDomain();
        LocalDate localDate = LocalDate.now();
        borrowDomain.setBeginDateBorrow(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        borrowDomain.setIdUser(1L);
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
                modelAndView.addObject("books",bookRepository.findAllByTitleBookOrAuthorBookContaining(contentSearch)   );
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
