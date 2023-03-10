package com.example.batisproject.service.yk.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.batisproject.dto.ChattingDTO;
import com.example.batisproject.dto.GatherCommentDTO;
import com.example.batisproject.dto.UserDTO;
import com.example.batisproject.entity.GatherComment;
import com.example.batisproject.mapper.yk.Yk_gather_commentMapper;
import com.example.batisproject.service.yk.Yk_gather_commentService;
import com.example.batisproject.service.yk.Yk_userSevice;

import com.example.batisproject.entity.GatherCommentMessage;
import com.example.batisproject.entity.User;
import com.example.batisproject.dto.ChattingDTO;


@Service
public class Yk_gather_commnetServiceImpl implements Yk_gather_commentService {
    
    @Autowired
    Yk_gather_commentService commentService;

    @Autowired
    Yk_gather_commentMapper commentMapper;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    Yk_userSevice yUserSevice;

    @Override
    public int joinComment(GatherCommentDTO commentDTO) {
        
        GatherComment comment = modelMapper.map(commentDTO, GatherComment.class);
        

        return commentMapper.joinComment(comment);
    }

    @Override
    public GatherCommentDTO get_gather_userRole(Long g_id, Long u_id) {
        GatherComment comment = commentMapper.get_gather_userRole(g_id, u_id);
        GatherCommentDTO checkNullDTO = new GatherCommentDTO();

        try {
            GatherCommentDTO commentDTO = modelMapper.map(comment, GatherCommentDTO.class);
            return commentDTO;
        } catch (Exception e) {
            // TODO: handle exception
            return checkNullDTO;
        }

        
    }

    @Override
    public int peopleCount(Long g_id) {
        int result = commentMapper.peopleCount(g_id);
        return result;
    }



    @Override
    public int checkRole(GatherCommentDTO commentDTO) {
        GatherComment comment = modelMapper.map(commentDTO, GatherComment.class);
        String role= commentMapper.checkRole(comment);
        //?????? ??????????????? ??????????????? ???????????? ????????? ?????? ????????? null?????? ???????????????????????? ????????? ?????? 0
        try {
            //????????? ????????? ????????????????????? ??????????????? ????????? ???????????? ????????? ?????????
            if(role.equals(null)){
               return 0; 
            }
        } catch (Exception e) {
            //?????? ???????????? ????????? ????????????
            return 0; 
        }
        
        return Integer.parseInt(role);
    }

    @Override
    public int joinCancel(GatherCommentDTO commentDTO) {
        GatherComment comment = modelMapper.map(commentDTO, GatherComment.class);
        int result = commentMapper.joinCancel(comment);
        
        return result;
    }


    @Override
    public int registerComment(GatherCommentDTO commentDTO) {
        GatherComment comment = modelMapper.map(commentDTO, GatherComment.class);
        
        return commentMapper.registerComment(comment);
    }

    @Override
    public int againJoin(GatherCommentDTO commentDTO) {
        GatherComment comment = modelMapper.map(commentDTO, GatherComment.class);
        
        return commentMapper.againJoin(comment);
    }

    //??????????????? ?????? ????????????
    @Override
    public List<GatherCommentDTO> getJoinList(Long g_id) {
        List<GatherComment> joinListBefor = commentMapper.getJoinList(g_id);

        List<GatherCommentDTO> joinList = joinListBefor.stream()
        .map(GatherComment->modelMapper.map(GatherComment, GatherCommentDTO.class))
        .collect(Collectors.toList());

        return joinList;
    }

    @Override
    public int joinOk(GatherCommentDTO commentDTO) {
        GatherComment comment = modelMapper.map(commentDTO, GatherComment.class);
        commentMapper.joinOk(comment);
        return commentMapper.joinOk(comment);
    }

    //???????????? ??????????????? ???????????? ?????? ????????? ???????????? ???????????? 
    @Override
    public List<UserDTO> nicknameList(List<GatherCommentDTO> joinList) {
        List<UserDTO> userIdList= new ArrayList<>();
        
        

        for(int i=0; i<joinList.size(); i++){
            Long uId=joinList.get(i).getUser();
            userIdList.add(yUserSevice.idToNick(uId));
            
        }
        
        

        return userIdList;
    }

    @Override
    public int deleteGatherIdTocomment(Long g_id) {
        
        return commentMapper.deleteGatherIdTocomment(g_id);
    }


    //?????? ?????? ??????
    @Override
    public int joinOks(Long[] userId,Long g_id) {
        GatherCommentDTO commentDTO = new GatherCommentDTO();
        commentDTO.setGather(g_id);
        for(int i=0; i<userId.length; i++ ){
             commentDTO.setUser(userId[i]);
             int result =commentService.joinOk(commentDTO);
             if(result<0){
                return 0;
             }
        }


        return 1;
    }

    //?????? ?????? ??????
    @Override
    public int joinNos(Long[] userId,Long g_id) {
        GatherCommentDTO commentDTO = new GatherCommentDTO();
        commentDTO.setGather(g_id);
        for(int i=0; i<userId.length; i++ ){
             commentDTO.setUser(userId[i]);
             int result =commentService.deleteJoinCancel(commentDTO);
             if(result<0){
                return 0;
             }
        }


        return 1;
    }

    @Override
    public List<ChattingDTO>findCommentList(Long g_id){
        Long[] gcArray = commentMapper.toFindGcIdList(g_id);

        List<ChattingDTO> chattingList = new ArrayList<>();
        
        
        for(int i=0; i<gcArray.length; i++){
            
            List<GatherCommentMessage> messge =  commentMapper.findCommentList(gcArray[i]);
            User user = commentMapper.toMessageFindUser(gcArray[i]);
            for(int j=0; j<messge.size(); j++){
                ChattingDTO chattingDTO = new ChattingDTO();
                chattingDTO.setMessageId(messge.get(j).getId());
                chattingDTO.setBody(messge.get(j).getBody());
                chattingDTO.setUser(user.getId());
                chattingDTO.setUserNick(user.getNickname());

                chattingList.add(chattingDTO);
            }


        }  
        Collections.sort(chattingList);

        return chattingList;
    }



    

    //?????????????????????????????? ?????????
    @Override
    public List<GatherCommentDTO> toJoinList(Long g_id) {
        List<GatherComment> joinListBefor = commentMapper.toJoinList(g_id);

        List<GatherCommentDTO> joinList = joinListBefor.stream()
        .map(GatherComment->modelMapper.map(GatherComment, GatherCommentDTO.class))
        .collect(Collectors.toList());

        


        return joinList;
    }

    @Override
    public int chattingCommentRegister (Long g_id,ChattingDTO chattingDTO){
        Long gc_id =commentMapper.findChattingGcId(g_id, chattingDTO.getUser());
        
       


        return commentMapper.chattingCommentRegister(gc_id, chattingDTO);
    }

    @Override
    public int deleteMessage(Long gcm_id) {
        
        return commentMapper.deleteMessage(gcm_id);
    }


    //?????????
    @Override
    public int deleteJoinCancel(GatherCommentDTO commentDTO) {
        GatherComment comment = modelMapper.map(commentDTO, GatherComment.class);
        int result = commentMapper.deleteJoinCancel(comment);
        
        return result;
    }

  


}
