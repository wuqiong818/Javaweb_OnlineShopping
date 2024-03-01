CREATE TABLE `merchandise` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '商品的唯一标识',
  `name` varchar(255) NOT NULL COMMENT '商品名称',
  `documentPath` text,
  `photoPath` text COMMENT '商品图片的路径，用分号进行分割',
  `feature` varchar(255) DEFAULT NULL COMMENT '商品的特征，多个的话，采用分号进行分割',
  `description` text NOT NULL COMMENT '商品的详情描述',
  `category` varchar(255) NOT NULL COMMENT '商品的分类',
  `price` decimal(10,2) NOT NULL COMMENT '商品的价格',
  `amount` int NOT NULL COMMENT '商品的数量',
  `merchantId` int DEFAULT NULL,
  `merchandiseStatus` tinyint(1) DEFAULT NULL COMMENT '0为售空1为正常2下架,3为商家或者管理员删除',
  `issuedDate` datetime NOT NULL COMMENT '发行时间',
  PRIMARY KEY (`id`),
  KEY `merchandise_user_null_fk` (`merchantId`),
  CONSTRAINT `merchandise_user_null_fk` FOREIGN KEY (`merchantId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品信息'

CREATE TABLE `merchandise_shoppingcart` (
  `id` int NOT NULL AUTO_INCREMENT,
  `merchandiseId` int DEFAULT NULL,
  `shoppingCartId` int DEFAULT NULL,
  `amount` int DEFAULT NULL,
  `isChecked` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `商品id` (`merchandiseId`),
  KEY `购物车` (`shoppingCartId`),
  CONSTRAINT `商品id` FOREIGN KEY (`merchandiseId`) REFERENCES `merchandise` (`id`),
  CONSTRAINT `购物车` FOREIGN KEY (`shoppingCartId`) REFERENCES `shoppingcart_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `order_merchandise` (
  `id` int NOT NULL AUTO_INCREMENT,
  `orderId` int DEFAULT NULL,
  `merchandiseId` int DEFAULT NULL,
  `merchantId` int DEFAULT NULL,
  `amount` int DEFAULT NULL,
  `total` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_merchandise_merchandise_null_fk` (`merchandiseId`),
  KEY `order_merchandise_order_null_fk` (`orderId`),
  CONSTRAINT `order_merchandise_merchandise_null_fk` FOREIGN KEY (`merchandiseId`) REFERENCES `merchandise` (`id`),
  CONSTRAINT `order_merchandise_order_null_fk` FOREIGN KEY (`orderId`) REFERENCES `order` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `exchangeMeans` varchar(255) DEFAULT NULL,
  `purchaserId` int DEFAULT NULL,
  `receiverName` varchar(20) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `notes` text,
  `orderTotal` decimal(10,2) DEFAULT NULL,
  `orderStatus` varchar(255) DEFAULT NULL,
  `purchaseDate` datetime DEFAULT NULL,
  `merchantId` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_user_null_fk` (`merchantId`),
  KEY `order_user_id_fk` (`purchaserId`),
  CONSTRAINT `order_user_id_fk` FOREIGN KEY (`purchaserId`) REFERENCES `user` (`id`),
  CONSTRAINT `order_user_null_fk` FOREIGN KEY (`merchantId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=122 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `shoppingcart_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userId` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userid` (`userId`),
  CONSTRAINT `shoppingcart_user_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户的唯一标识',
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `profilePath` varchar(255) DEFAULT NULL COMMENT '头像路径',
  `userStatus` varchar(255) DEFAULT '1' COMMENT '用户状态,0是禁用，1是正常',
  `telephone` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '手机号',
  `address` varchar(255) DEFAULT NULL COMMENT '收货地址',
  `identity` int DEFAULT '0' COMMENT '身份的验证，0和null表示普通用户，1表示管理员',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_pk` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
