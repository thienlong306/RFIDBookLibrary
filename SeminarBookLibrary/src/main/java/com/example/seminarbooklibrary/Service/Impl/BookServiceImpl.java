package com.example.seminarbooklibrary.Service.Impl;



import com.caen.RFIDLibrary.*;
import com.example.seminarbooklibrary.Domain.BookDomain;
import com.example.seminarbooklibrary.Domain.TagReadDomain;
import com.example.seminarbooklibrary.Model.TagReadModel;
import com.example.seminarbooklibrary.Repository.BookRepository;
import com.example.seminarbooklibrary.Service.BookService;
import com.example.seminarbooklibrary.Service.TagReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Service
@SessionScope
public class BookServiceImpl implements BookService {
    private ArrayList<Long> listIdBook = new ArrayList<>();
    private ArrayList<TagReadModel> listInfoRfid = new ArrayList<>();
    @Autowired
    BookRepository bookRepository;
    @Autowired
    TagReadService tagReadService;

    @Override
    public ArrayList<Long> getListIdBook() {
        return listIdBook;
    }

    @Override
    public void setListIdBook(ArrayList<Long> listIdBook) {
        this.listIdBook = listIdBook;
    }

    @Override
    public void addBookBorrow(Long idbook) {
        listIdBook.add(idbook);
    }

    @Override
    public void deleteBookBorrow(Long idbook){listIdBook.remove(idbook);}

    @Override
    public void clearListIdBook() {
        listIdBook.clear();
    }

    @Override
    public List<BookDomain> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<BookDomain> findAllById(Iterable<Long> longs) {
        return bookRepository.findAllById(longs);
    }

    @Override
    public List<BookDomain> findByTitleBookContaining(String titleBook) {
        return bookRepository.findByTitleBookContaining(titleBook);
    }

    @Override
    public List<BookDomain> findByAuthorBookContaining(String authorBook) {
        return bookRepository.findByAuthorBookContaining(authorBook);
    }

    @Override
    public BookDomain findTopByOrderByIdBookDesc() {
        return bookRepository.findTopByOrderByIdBookDesc();
    }

    @Override
    public void deleteById(Long aLong) {
        bookRepository.deleteById(aLong);
    }

    @Override
    public <S extends BookDomain> S saveAndFlush(S entity) {
        return bookRepository.saveAndFlush(entity);
    }

    @Override
    public <S extends BookDomain> List<S> saveAllAndFlush(Iterable<S> entities) {
        return bookRepository.saveAllAndFlush(entities);
    }

    @Override
    public BookDomain getById(Long aLong) {
        return bookRepository.getById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return bookRepository.existsById(aLong);
    }

    @Override
    public long count() {
        return bookRepository.count();
    }

    @Override
    public ArrayList<Long> getListIdBookByListRFIDTest() {
        return listIdBook;
    }
    @Override
    public ArrayList<TagReadModel> getListInfoRfidTest() {
        return listInfoRfid;
    }

    @Override
    public ArrayList<Long> getListIdBookByListRFID() throws Exception {
        ArrayList<String> listRfid = new ArrayList<>();
        listRfid.add("E28011606000020958CDC43E");
        listRfid.add("E28011606000020958CD98FE");
//        listRfid=ReadRFID();
        if (listRfid.size() != 0) {
            for (TagReadDomain tagReadModel : tagReadService.findAllById(listRfid)) {
                listIdBook.add(tagReadModel.getIdBook());
            }
        }
        return listIdBook;
    }
    @Override
    public ArrayList<TagReadModel> getListInfoRFID() {
        ArrayList<String> listRfid = new ArrayList<>();
        listInfoRfid.clear();
        listRfid.add("E28011606000020958CDC43E");
        listRfid.add("E28011606000020958CD98FF");
        listRfid.add("E28011606000020958CD98FE");
//        listRfid=ReadRFID();
        if (listRfid.size() != 0) {
            for (String rfid:listRfid) {
                try {
                    TagReadModel tagReadModel=new TagReadModel();
                    tagReadModel.setIdTagRead(rfid);
                    Long idBook=tagReadService.getById(rfid).getIdBook();
                    tagReadModel.setBookDomain(bookRepository.getById(idBook));
                    listInfoRfid.add(tagReadModel);
                } catch (Exception e) {
                    TagReadModel tagReadModel=new TagReadModel();
                    tagReadModel.setIdTagRead(rfid);
                    tagReadModel.setStatus(0);
                    listInfoRfid.add(tagReadModel);
                }
            }
        }
        return listInfoRfid;
    }

    public static ArrayList<String> ReadRFID() throws Exception {
        // TODO Auto-generated method stub
        ArrayList<String> listRfid = new ArrayList<>();
        CAENRFIDReader MyReader = new CAENRFIDReader();
        MyReader.Connect(CAENRFIDPort.CAENRFID_TCP, "192.168.1.2");
        CAENRFIDLogicalSource MySource = MyReader.GetSource("Source_0");
        //get Reader Infor
        CAENRFIDReaderInfo Info = MyReader.GetReaderInfo();
        String Model = Info.GetModel();
        String SerialNumber = Info.GetSerialNumber();
        String FWRelease = MyReader.GetFirmwareRelease();
        int power = MyReader.GetPower();

        System.out.println("Model: " + Model);
        System.out.println("SerialNumber: " + SerialNumber);
        System.out.println("FWRelease: " + FWRelease);
        System.out.println("power: " + power);

        MySource.SetSession_EPC_C1G2(CAENRFIDLogicalSourceConstants.EPC_C1G2_SESSION_S1);

        CAENRFIDTag[] MyTags;
        MyTags = MySource.InventoryTag();
        if (MyTags!=null){
            if (MyTags.length >0) {
                for (int i = 0; i < MyTags.length; i++) {
                    listRfid.add(hex(MyTags[i].GetId()));
                    System.out.println(
                            "	 EPC: " + hex(MyTags[i].GetId())
                                    + " | Antenna : " + MyTags[i].GetAntenna()
                                    + " | TID : " + MyTags[i].GetTID()
                                    + " | RSSI : " + Integer.valueOf(MyTags[i].GetRSSI()));
                }
            }
        }
        MyReader.Disconnect();
        return listRfid;
    }

    public static String hex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte aByte : bytes) {
            result.append(String.format("%02x", aByte));
            // upper case
            // result.append(String.format("%02X", aByte));
        }
        return result.toString().toUpperCase();
    }

}
