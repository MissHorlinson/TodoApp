CREATE TABLE `listtodo` (
                            `list_id` int(11) NOT NULL AUTO_INCREMENT,
                            `list_title` varchar(255) DEFAULT NULL,
                            PRIMARY KEY (`list_id`)
)

CREATE TABLE `itemtodo` (
                            `item_id` int(11) NOT NULL AUTO_INCREMENT,
                            `item_index` int(11) DEFAULT NULL,
                            `text` varchar(150) DEFAULT NULL,
                            `checked` tinyint(4) DEFAULT NULL,
                            `list_id` int(11) DEFAULT NULL,
                            PRIMARY KEY (`item_id`),
                            KEY `list_fk_idx` (`list_id`),
                            CONSTRAINT `list_fk` FOREIGN KEY (`list_id`) REFERENCES `listtodo` (`list_id`)
)