-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1:8080
-- Thời gian đã tạo: Th4 30, 2022 lúc 07:33 AM
-- Phiên bản máy phục vụ: 10.4.11-MariaDB
-- Phiên bản PHP: 7.4.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `book`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `books`
--

CREATE TABLE `books` (
  `book_id` bigint(20) NOT NULL,
  `book_author` varchar(255) DEFAULT NULL,
  `book_status` int(11) DEFAULT NULL,
  `book_title` varchar(255) DEFAULT NULL,
  `book_img` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `books`
--

INSERT INTO `books` (`book_id`, `book_author`, `book_status`, `book_title`, `book_img`) VALUES
(1, 'Andre Aciman', 0, 'Call Me By Your Name', 'Call Me By Your Name.jpg'),
(2, 'Margaret Mitchell', 0, 'Gone With The Wind', 'Gone With The Wind.jpg'),
(3, 'Colleen McCullough', 0, 'The Thorn Birds', 'The Thorn Birds.jpg'),
(4, 'Haruki Murakami', 1, 'Kafka On The Shore', 'Kafka On The Shore.jpg'),
(37, 'Test AuthorRR', 1, 'TestRRR', 'Test.jpg');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `borrow`
--

CREATE TABLE `borrow` (
  `borrow_id` bigint(20) NOT NULL,
  `borrow_begindate` date DEFAULT NULL,
  `borrow_enddate` date DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `borrow_returndate` date DEFAULT NULL,
  `borrrow_status` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `borrow`
--

INSERT INTO `borrow` (`borrow_id`, `borrow_begindate`, `borrow_enddate`, `user_id`, `borrow_returndate`, `borrrow_status`) VALUES
(25, '2022-04-22', '2022-04-26', 9, NULL, 0),
(24, '2022-04-20', '2022-04-24', 7, NULL, 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `borrow_detail`
--

CREATE TABLE `borrow_detail` (
  `borrowdetail_id` bigint(20) NOT NULL,
  `book_id` bigint(20) DEFAULT NULL,
  `borrow_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `borrow_detail`
--

INSERT INTO `borrow_detail` (`borrowdetail_id`, `book_id`, `borrow_id`) VALUES
(33, 3, 25),
(32, 2, 24),
(31, 1, 24);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tag_read`
--

CREATE TABLE `tag_read` (
  `tag_rfid` varchar(255) NOT NULL,
  `book_id` bigint(20) DEFAULT NULL,
  `tag_time` date DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `tag_read`
--

INSERT INTO `tag_read` (`tag_rfid`, `book_id`, `tag_time`) VALUES
('E28011606000020958CDF86E', 1, '2022-04-12'),
('E28011606000020958CDC43E', 3, '2022-04-12'),
('E28011606000020958CD98FE', 2, '2022-04-12'),
('E28011606000020958CD98EE', 4, '2022-04-12'),
('00B07A14285276D030000082', 37, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL,
  `user_loginname` varchar(255) DEFAULT NULL,
  `user_loginpassword` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `user_phone` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`user_id`, `user_loginname`, `user_loginpassword`, `user_name`, `user_phone`) VALUES
(8, 'admin', '123', 'Long', '090'),
(7, 'Test', '123', 'long', '0808'),
(9, 'Test1', '123', 'Vu', '094');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`book_id`);

--
-- Chỉ mục cho bảng `borrow`
--
ALTER TABLE `borrow`
  ADD PRIMARY KEY (`borrow_id`);

--
-- Chỉ mục cho bảng `borrow_detail`
--
ALTER TABLE `borrow_detail`
  ADD PRIMARY KEY (`borrowdetail_id`);

--
-- Chỉ mục cho bảng `tag_read`
--
ALTER TABLE `tag_read`
  ADD PRIMARY KEY (`tag_rfid`);

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `books`
--
ALTER TABLE `books`
  MODIFY `book_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- AUTO_INCREMENT cho bảng `borrow`
--
ALTER TABLE `borrow`
  MODIFY `borrow_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT cho bảng `borrow_detail`
--
ALTER TABLE `borrow_detail`
  MODIFY `borrowdetail_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT cho bảng `user`
--
ALTER TABLE `user`
  MODIFY `user_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
