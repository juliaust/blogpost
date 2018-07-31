CREATE DATABASE IF NOT EXISTS blogpost;

USE blogpost;

DROP TABLE IF EXISTS user_upvoted_post;
DROP TABLE IF EXISTS user_downvoted_post;
DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS post_user;

CREATE table post_user(
	id bigint(20) NOT NULL auto_increment,
	local_id bigint(20) NOT NULL,
    `name` varchar(50) DEFAULT NULL,
    PRIMARY KEY (id)
) ENGINE=INNODB; 


CREATE TABLE post(
	id bigint(20) NOT NULL auto_increment,
    user_id bigint(20) NOT NULL,
    url varchar(1000) DEFAULT NULL,
    post_name varchar(64) DEFAULT NULL,
    vote_count int(11) default 0,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY user_id (`user_id`),
	CONSTRAINT `posts_ibfk_1` 
      FOREIGN KEY (`user_id`) REFERENCES `post_user` (`id`)  ON DELETE CASCADE
) ENGINE=INNODB;

CREATE TABLE `user_upvoted_post` (
  `user_id` bigint(20) NOT NULL,
  `post_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`post_id`),
  KEY `post_id` (`post_id`),
  CONSTRAINT `user_upvoted_post_ibfk_1` 
   FOREIGN KEY (`user_id`) REFERENCES `post_user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `user_upvoted_post_ibfk_2` 
   FOREIGN KEY (`post_id`) REFERENCES `post` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE `user_downvoted_post` (
  `user_id` bigint(20) NOT NULL,
  `post_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`post_id`),
  KEY `post_id` (`post_id`),
  CONSTRAINT `user_downvoted_post_ibfk_1` 
   FOREIGN KEY (`user_id`) REFERENCES `post_user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `user_downvoted_post_ibfk_2` 
   FOREIGN KEY (`post_id`) REFERENCES `post` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB;

-- INSERT INTO `user`(id, `name`) values (1, 'Julia'), (2, 'Momo');
-- INSERT INTO post(id, user_id) values (1, 1), (2, 1), (3, 1), (4, 2), (5,2); 