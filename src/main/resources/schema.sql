-- 1. tbl_user
CREATE TABLE IF NOT EXISTS `tbl_user` (
                                          `id` BIGINT NOT NULL AUTO_INCREMENT,
                                          `name` VARCHAR(10) NOT NULL,
                                          `email` VARCHAR(40) NOT NULL,
                                          `stu_num` VARCHAR(10) NOT NULL,
                                          `user_role` VARCHAR(10) NOT NULL,
                                          `password` CHAR(60) NOT NULL,
                                          `conference_member_id` BIGINT,
                                          `lecture_member_id` BIGINT,
                                          PRIMARY KEY (`id`)
);

-- 2. tbl_conference
CREATE TABLE IF NOT EXISTS `tbl_conference` (
                                                `id` BIGINT NOT NULL AUTO_INCREMENT,
                                                `title` VARCHAR(50) NOT NULL,
                                                `content` VARCHAR(1000) NOT NULL,
                                                `conference_date` DATE NOT NULL,
                                                `start_register_date` DATE NOT NULL,
                                                `end_register_date` DATE NOT NULL,
                                                `user_id` BIGINT NOT NULL,
                                                PRIMARY KEY (`id`)
);

-- 3. tbl_lecture
CREATE TABLE IF NOT EXISTS `tbl_lecture` (
                                             `id` BIGINT NOT NULL AUTO_INCREMENT,
                                             `title` VARCHAR(50) NOT NULL,
                                             `content` VARCHAR(1000) NOT NULL,
                                             `presenter_name` VARCHAR(50) NOT NULL,
                                             `section` VARCHAR(5) NOT NULL,
                                             `conference_date` DATE NOT NULL,
                                             `start_register_date` DATE NOT NULL,
                                             `end_register_date` DATE NOT NULL,
                                             PRIMARY KEY (`id`)
);

-- 4. tbl_conference_member
CREATE TABLE IF NOT EXISTS `tbl_conference_member` (
                                                       `id` BIGINT NOT NULL AUTO_INCREMENT,
                                                       `conference_id` BIGINT NOT NULL,
                                                       PRIMARY KEY (`id`)
);

-- 5. tbl_lecture_member
CREATE TABLE IF NOT EXISTS `tbl_lecture_member` (
                                                    `id` BIGINT NOT NULL AUTO_INCREMENT,
                                                    `lecture_id` BIGINT NOT NULL,
                                                    PRIMARY KEY (`id`)
);

-- 6. tbl_conference_request
CREATE TABLE IF NOT EXISTS `tbl_conference_request` (
                                                        `id` BIGINT NOT NULL AUTO_INCREMENT,
                                                        `user_id` BIGINT NOT NULL,
                                                        `title` VARCHAR(50) NOT NULL,
                                                        `content` VARCHAR(1000) NOT NULL,
                                                        PRIMARY KEY (`id`)
);

-- 외래 키 제약조건 추가
ALTER TABLE `tbl_user` ADD CONSTRAINT `FK_tbl_conference_member_TO_tbl_user_1` FOREIGN KEY (`conference_member_id`) REFERENCES `tbl_conference_member`(`id`);
ALTER TABLE `tbl_user` ADD CONSTRAINT `FK_tbl_lecture_member_TO_tbl_user_1` FOREIGN KEY (`lecture_member_id`) REFERENCES `tbl_lecture_member`(`id`);
ALTER TABLE `tbl_conference_member` ADD CONSTRAINT `FK_tbl_conference_TO_tbl_conference_member_1` FOREIGN KEY (`conference_id`) REFERENCES `tbl_conference`(`id`);
ALTER TABLE `tbl_lecture_member` ADD CONSTRAINT `FK_tbl_lecture_TO_tbl_lecture_member_1` FOREIGN KEY (`lecture_id`) REFERENCES `tbl_lecture`(`id`);
ALTER TABLE `tbl_conference_request` ADD CONSTRAINT `FK_tbl_user_TO_tbl_conference_request_1` FOREIGN KEY (`user_id`) REFERENCES `tbl_user`(`id`);
ALTER TABLE `tbl_conference` ADD CONSTRAINT `FK_tbl_user_TO_tbl_conference_1` FOREIGN KEY (`user_id`) REFERENCES `tbl_user`(`id`);
