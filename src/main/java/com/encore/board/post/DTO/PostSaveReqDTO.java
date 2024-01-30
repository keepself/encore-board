package com.encore.board.post.DTO;

import lombok.Data;

@Data
public class PostSaveReqDTO {
        private String title;
        private String contents;
        private String appointment;
        private String appointmentTime;
}
