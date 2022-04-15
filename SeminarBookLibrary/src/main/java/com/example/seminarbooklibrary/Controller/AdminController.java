package com.example.seminarbooklibrary.Controller;

import com.caen.RFIDLibrary.CAENRFIDException;
import com.example.seminarbooklibrary.Domain.BookDomain;
import com.example.seminarbooklibrary.Domain.BorrowDetailDomain;
import com.example.seminarbooklibrary.Domain.BorrowDomain;
import com.example.seminarbooklibrary.Domain.TagReadDomain;
import com.example.seminarbooklibrary.Model.BookModel;
import com.example.seminarbooklibrary.Model.BorrowModel;
import com.example.seminarbooklibrary.Model.UserModel;
import com.example.seminarbooklibrary.Repository.BorrowDetailRepository;
import com.example.seminarbooklibrary.Repository.BorrowRepository;
import com.example.seminarbooklibrary.Service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    BookService bookService;
    @Autowired
    BorrowService borrowService;
    @Autowired
    BorrowDetailService borrowDetailService;
    @Autowired
    StorageService storageService;
    @Autowired
    TagReadService tagReadService;
    @GetMapping("")
    public ModelAndView getHome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/Home");
        return modelAndView;
    }
    @GetMapping("borrow")
    public ModelAndView getScanBorrow() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("books", bookService.findAllById(bookService.getListIdBookByListRFIDTest()));
        modelAndView.addObject("user", new UserModel());
        modelAndView.setViewName("admin/BorrowBookOffline");
        return modelAndView;
    }
    @GetMapping("scan")
    public ModelAndView getScanBook(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("rfids", bookService.getListInfoRfidTest());
        modelAndView.setViewName("admin/ScanBook");
        return modelAndView;
    }
    @GetMapping("managerBook")
    public ModelAndView getManagerBook() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("books", bookService.findAll());
        modelAndView.setViewName("admin/ManagerBook");
        return modelAndView;
    }
    @GetMapping("managerBorrow")
    public ModelAndView getManagerBorrow() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("listInfoBorrow",borrowService.getListBorrowModel());
        modelAndView.setViewName("admin/ManagerBorrow");
        return modelAndView;
    }
    @GetMapping("managerBorrow/details/{idBorrow}")
    public ModelAndView getDetailsBorrow(@PathVariable("idBorrow") Long idBorrow) {
        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("listInfoBorrow",borrowService.getListBorrowModel());
        ArrayList<Long> listBook=new ArrayList<>();
        for (BorrowDetailDomain borrowDetailDomain:borrowDetailService.findAllByIdBorrow(idBorrow)){
            listBook.add(borrowDetailDomain.getIdBook());
        }
        modelAndView.addObject("books",bookService.findAllById(listBook));
        modelAndView.setViewName("admin/DetailsBorrow");
        return modelAndView;
    }
    @GetMapping("scan/addOrEdit/{idTagRead}")
    public ModelAndView getAddOrEditBook(@PathVariable("idTagRead") String idTagRead) {
        ModelAndView modelAndView = new ModelAndView();
        BookModel bookModel=new BookModel();
        bookModel.setIdTagRead(idTagRead);
        modelAndView.addObject("book",bookModel);
        modelAndView.setViewName("admin/AddEditBook");
        return modelAndView;
    }
    @GetMapping("/data/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename){
        Resource file=storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=\""+file.getFilename()+"\"").body(file);
    }
    @PostMapping("scan/addOrEdit")
    public ModelAndView postAddOrEditBook(@Valid @ModelAttribute("book")BookModel bookModel) {
        ModelAndView modelAndView = new ModelAndView();
        bookModel.setStatusBook(1);
        bookModel.setImgBook(bookModel.getTitleBook()+".jpg");
        try {
            InputStream inputStream=new BufferedInputStream(bookModel.getImgFile().getInputStream());
            Path path = Paths.get("uploads/images/"+bookModel.getImgBook());
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BookDomain entity = new BookDomain();
        BeanUtils.copyProperties(bookModel,entity);
        bookService.saveAndFlush(entity);
        TagReadDomain tagReadDomain=new TagReadDomain();
        tagReadDomain.setIdTagRead(bookModel.getIdTagRead());
        tagReadDomain.setIdBook(bookService.findTopByOrderByIdBookDesc().getIdBook());
        tagReadService.saveAndFlush(tagReadDomain);
        modelAndView.setViewName("redirect:/admin/managerBook");
        return modelAndView;
    }
    @GetMapping("delete/{idBook}")
    public ModelAndView getDelete(@PathVariable("idBook") Long idBook, HttpServletRequest httpServletRequest) {
        ModelAndView modelAndView = new ModelAndView();
        bookService.deleteById(idBook);
        tagReadService.deleteById(tagReadService.findByIdBook(idBook).getIdTagRead());
        modelAndView.setViewName("redirect:"+httpServletRequest.getHeader("Referer"));
        return modelAndView;
    }
    @PostMapping("scanBorrow")
    public ModelAndView postScanBorrow() throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        bookService.clearListIdBook();
        modelAndView.addObject("books", bookService.findAllById(bookService.getListIdBookByListRFID()));
        modelAndView.addObject("user", new UserModel());
        modelAndView.setViewName("redirect:/admin/borrow");
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
        modelAndView.setViewName("redirect:/admin");
        return modelAndView;
    }
    @PostMapping("scanBook")
    public ModelAndView postScanBook() throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("rfids", bookService.getListInfoRFID());
        modelAndView.setViewName("redirect:/admin/scan");
        return modelAndView;
    }
    @PostMapping("managerBook")
    public ModelAndView postManagerBook() throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        if (bookService.getListIdBookByListRFID().size()!=0){
            modelAndView.addObject("books", bookService.findAllById(bookService.getListIdBookByListRFID()));
        }
        modelAndView.setViewName("redirect:/admin/managerBook");
        return modelAndView;
    }
    @GetMapping("search")
    public ModelAndView postSearchManagerBorrow(@RequestParam(value = "nameUser",required = false) String nameUser,@RequestParam(value = "dateBegin",required = false) String dateBegin,@RequestParam(value = "dateEnd",required = false) String dateEnd){
        ModelAndView modelAndView=new ModelAndView();
        Date dateFrom = null;
        Date dateTo=null;
        try {
            if (!dateBegin.isEmpty()){
                dateFrom = new SimpleDateFormat("MM/dd/yyyyy").parse(dateBegin);
            }
            if (!dateEnd.isEmpty()) {
                dateTo = new SimpleDateFormat("MM/dd/yyyyy").parse(dateEnd);
            }else
                dateTo=new Date();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        modelAndView.addObject("listInfoBorrow",borrowService.getListBorrowModelByBorrower(nameUser,dateFrom,dateTo));
        modelAndView.setViewName("admin/ManagerBorrow");
        return modelAndView;
    }
}
