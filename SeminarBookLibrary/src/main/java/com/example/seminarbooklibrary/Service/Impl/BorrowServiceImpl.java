package com.example.seminarbooklibrary.Service.Impl;

import com.example.seminarbooklibrary.Domain.BookDomain;
import com.example.seminarbooklibrary.Domain.BorrowDomain;
import com.example.seminarbooklibrary.Domain.UserDomain;
import com.example.seminarbooklibrary.Model.BorrowModel;
import com.example.seminarbooklibrary.Repository.BookRepository;
import com.example.seminarbooklibrary.Repository.BorrowRepository;
import com.example.seminarbooklibrary.Repository.UserRepository;
import com.example.seminarbooklibrary.Service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BorrowServiceImpl implements BorrowService {
    @Autowired
    BorrowRepository borrowRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public List<BorrowDomain> findAll() {
        return borrowRepository.findAll();
    }

    @Override
    public List<BorrowDomain> findAllById(Iterable<Long> longs) {
        return borrowRepository.findAllById(longs);
    }

    @Override
    public <S extends BorrowDomain> List<S> saveAll(Iterable<S> entities) {
        return borrowRepository.saveAll(entities);
    }

    @Override
    public <S extends BorrowDomain> S saveAndFlush(S entity) {
        return borrowRepository.saveAndFlush(entity);
    }

    @Override
    public BorrowDomain getById(Long aLong) {
        return borrowRepository.getById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return borrowRepository.existsById(aLong);
    }

    @Override
    public long count() {
        return borrowRepository.count();
    }

    @Override
    public BorrowDomain findTopByOrderByIdBorrowDesc() {
        return borrowRepository.findTopByOrderByIdBorrowDesc();
    }

    @Override
    public ArrayList<BorrowModel> getListBorrowModel(){
        ArrayList<BorrowModel> listBorrowModel=new ArrayList<>();
        List<BorrowDomain> listborrowDomain=borrowRepository.findAll();
        for (BorrowDomain borrowDomain:listborrowDomain){
            BorrowModel borrowModel=new BorrowModel();
            borrowModel.setIdBorrow(borrowDomain.getIdBorrow());
            borrowModel.setBeginDateBorrow(borrowDomain.getBeginDateBorrow());
            borrowModel.setEndDateBorrow(borrowDomain.getEndDateBorrow());
            borrowModel.setIdUser(borrowDomain.getIdUser());
            borrowModel.setStatusBorrow(borrowDomain.getStatusBorrow());
            borrowModel.setReturnDateBorrow(borrowDomain.getReturnDateBorrow());
            borrowModel.setUserDomain(userRepository.getById(borrowDomain.getIdUser()));
            listBorrowModel.add(borrowModel);
        }
        return listBorrowModel;
    }
    @Override
    public ArrayList<BorrowModel> getListUserBorrowModel(Long idUser){
        ArrayList<BorrowModel> listBorrowModel=new ArrayList<>();
        List<BorrowDomain> listborrowDomain=borrowRepository.findAllByIdUser(idUser);
        for (BorrowDomain borrowDomain:listborrowDomain){
            BorrowModel borrowModel=new BorrowModel();
            borrowModel.setIdBorrow(borrowDomain.getIdBorrow());
            borrowModel.setBeginDateBorrow(borrowDomain.getBeginDateBorrow());
            borrowModel.setEndDateBorrow(borrowDomain.getEndDateBorrow());
            borrowModel.setIdUser(borrowDomain.getIdUser());
            borrowModel.setStatusBorrow(borrowDomain.getStatusBorrow());
            borrowModel.setReturnDateBorrow(borrowDomain.getReturnDateBorrow());
            borrowModel.setUserDomain(userRepository.getById(borrowDomain.getIdUser()));
            listBorrowModel.add(borrowModel);
        }
        return listBorrowModel;
    }
    @Override
    public ArrayList<BorrowModel> getListBorrowModelByBorrower(String nameUser, Date dateFrom, Date dateTo){
        ArrayList<BorrowModel> listBorrowModel=new ArrayList<>();
        List<BorrowDomain> listborrowDomainTmp=new ArrayList<>();
        List<BorrowDomain> listborrowDomain=borrowRepository.findAll();
        System.out.println(dateFrom+"-"+dateTo);
        for (BorrowDomain borrowDomain:listborrowDomain){
            if (dateFrom!=null) {
                if (borrowDomain.getBeginDateBorrow().after(dateFrom)||borrowDomain.getBeginDateBorrow().equals(dateFrom))
                    listborrowDomainTmp.add(borrowDomain);
                else if (borrowDomain.getEndDateBorrow().before(dateTo)||borrowDomain.getEndDateBorrow().equals(dateTo))
                        listborrowDomainTmp.add(borrowDomain);
            }
            else if (borrowDomain.getEndDateBorrow().before(dateTo)||borrowDomain.getEndDateBorrow().equals(dateTo))
                listborrowDomainTmp.add(borrowDomain);
        }
        if (listborrowDomainTmp.size()!=0){
            for (BorrowDomain borrowDomain:listborrowDomainTmp){
                UserDomain userDomain =userRepository.getById(borrowDomain.getIdUser());
                if (userDomain.getNameUser().contains(nameUser)){
                    BorrowModel borrowModel=new BorrowModel();
                    borrowModel.setIdBorrow(borrowDomain.getIdBorrow());
                    borrowModel.setBeginDateBorrow(borrowDomain.getBeginDateBorrow());
                    borrowModel.setEndDateBorrow(borrowDomain.getEndDateBorrow());
                    borrowModel.setIdUser(borrowDomain.getIdUser());
                    borrowModel.setStatusBorrow(borrowDomain.getStatusBorrow());
                    borrowModel.setReturnDateBorrow(borrowDomain.getReturnDateBorrow());
                    borrowModel.setUserDomain(userRepository.getById(borrowDomain.getIdUser()));
                    listBorrowModel.add(borrowModel);
                }
            }
        }
        return listBorrowModel;
    }

}
