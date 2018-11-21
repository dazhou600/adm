-- MySQL dump 10.13  Distrib 5.7.24, for Linux (x86_64)
--
-- Host: localhost    Database: bingge
-- ------------------------------------------------------
-- Server version	5.7.24-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping routines for database 'bingge'
--
/*!50003 DROP FUNCTION IF EXISTS `CountLayer` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `COUNTLAYER`(type_id int) RETURNS int(11)
begin
    declare result int default 0 ;
    declare lft int;
    declare rgt int;
    begin
        select nav.lft,nav.rgt into lft,rgt from nav_bar nav where nav.bid=type_id ;
       set result= (select count(*) from nav_bar nav where nav.lft <= lft and nav.rgt >= rgt);
    end ;
    return result;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `f_delNode` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `f_delNode`(type_id int) RETURNS int(11)
begin
  declare t_error,vrgt,vlft int DEFAULT 0;  
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET t_error=1; 
    if (select count(nav.bid) from nav_bar nav where nav.bid=type_id)=1 then
         SET SQL_SAFE_UPDATES = 0;
       select nav.rgt,nav.lft into vrgt,vlft from nav_bar nav where nav.bid=type_id;

DELETE FROM nav_bar  where lft>=vlft and rgt<=vrgt ;
update nav_bar set rgt=rgt-(vrgt+1-vlft) where rgt>vrgt; 
update nav_bar set lft=lft-(vrgt+1-vlft) where lft>vlft;	
	
    end if;

    SET SQL_SAFE_UPDATES = 1;
    RETURN t_error;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `addSubnode` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `addSubnode`(start_id int,crwz int, rname varchar(50) CHARSET utf8,out errnb int)
begin
  DECLARE vrgt,vlft,vlevel,t_error,sumson,vsrgt int DEFAULT 0;
  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET t_error=1;
  select lft,rgt,CountLayer(start_id) into vlft,vrgt,vlevel from nav_bar where bid=start_id;
    tscrwz :begin
    if vlft<1 or vrgt<2 then leave tscrwz ;end if;
	SET SQL_SAFE_UPDATES = 0;
	#为直接子类总数赋值
	select count(bid) into sumson from (SELECT bid FROM nav_bar where lft>vlft and rgt<vrgt and CountLayer(bid)=vlevel+1) as rz ;
    #在位置1插入也就是起始节点左边加一
    if crwz<=1 or sumson=0 then
		START TRANSACTION;    
		update nav_bar set lft=lft+2 where lft>vlft;
		update nav_bar set rgt=rgt+2 where rgt>vlft;
        insert into nav_bar(lft,rgt,rname) value(vlft+1,vlft+2,rname);
    end if;
    if crwz>1 and sumson<>0 then
    set crwz=crwz-1;
	 if crwz>sumson then
		#大于子节点总数放在最后
		set crwz=sumson;
     end if;
     select rgt into vsrgt from (select * from (SELECT (@i:=@i+1) i,bid,lft,rgt,rname FROM (SELECT @i:=0) bianh ,nav_bar where lft>vlft and rgt<vrgt  and CountLayer(bid)=vlevel+1 order by lft)  rz) bh where bh.i=crwz  ;
     START TRANSACTION;    
		update nav_bar set lft=lft+2 where lft>vsrgt;
		update nav_bar set rgt=rgt+2 where rgt>vsrgt;
        insert into nav_bar(lft,rgt,rname) value(vsrgt+1,vsrgt+2,rname);
    end if ;
		IF t_error = 1 THEN 
			set errnb=-1;
            ROLLBACK;    
        ELSE 
			set errnb=666;
            COMMIT;    
        END IF;
    end tscrwz;
	SET SQL_SAFE_UPDATES = 1;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `delSubNode` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `delSubNode`(in type_id int,out errnb int)
begin
  declare t_error,vrgt,vlft int DEFAULT 0;  
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET t_error=1; 
    if (select count(nav.bid) from nav_bar nav where nav.bid=type_id)=1 then
         SET SQL_SAFE_UPDATES = 0;
        START TRANSACTION;    
       select nav.rgt,nav.lft into vrgt,vlft from nav_bar nav where nav.bid=type_id;

DELETE FROM nav_bar  where lft>=vlft and rgt<=vrgt ;
update nav_bar set rgt=rgt-(vrgt+1-vlft) where rgt>vrgt; 
update nav_bar set lft=lft-(vrgt+1-vlft) where lft>vlft;	
		IF t_error = 1 THEN 
        set errnb = -1;
            ROLLBACK;
        ELSE   
        set errnb = 666;
            COMMIT;    
        END IF;
    end if;
    SET SQL_SAFE_UPDATES = 1;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `moveNode` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `moveNode`(p_id int,vbid int, mbwz int)
begin
  DECLARE vrgt,vlft,vlevel,t_error,vslft,vsrgt,yswz int DEFAULT 0;
 DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET t_error=1;
  select lft,rgt,CountLayer(p_id) into vlft,vrgt,vlevel from nav_bar where bid=p_id;
tscrwz :begin
    if vlevel<1 then leave tscrwz ;end if;
#创建临时表存储子类
drop table if exists _temp_movenode;
CREATE TEMPORARY TABLE _temp_movenode (i int,bid int(11),lft int,rgt int,primary key(bid));
	#临时表填充数据(按顺序排列的儿节点)
    insert into _temp_movenode(i,bid,lft,rgt) select i,bid,lft,rgt from (select * from (SELECT (@i:=@i+1) i,bid,lft,rgt FROM (SELECT @i:=0) bianh ,nav_bar where lft>vlft and rgt<vrgt  and CountLayer(bid)=vlevel+1 order by lft)  rz) bh ;
	#初始化变量，重新利用给原始节点赋值
    set vlft=0,vrgt= 0;
	select i,lft,rgt into yswz,vlft,vrgt from _temp_movenode where bid=vbid;
	select lft,rgt into vslft,vsrgt from _temp_movenode where i=mbwz;
   if vlft<1 or vslft<1 or vrgt<vlft or vsrgt<vslft then leave tscrwz; end if;
    SET SQL_SAFE_UPDATES = 0;
	START TRANSACTION;
    delete from _temp_movenode;
    #待移动节点及其子类放入临时表
    insert into _temp_movenode(bid) select bid from nav_bar where lft>=vlft and rgt<=vrgt;
   #向上移动
    if yswz>mbwz  then
		update nav_bar set lft=lft+(vrgt-vlft+1), rgt=rgt+(vrgt-vlft+1) where lft>=vslft and rgt<vlft;
        update nav_bar set lft=lft-(vlft-vslft),rgt=rgt-(vlft-vslft) where bid in (select bid from _temp_movenode);
    end if;
    if yswz<mbwz then
		update nav_bar  set lft=lft-(vrgt-vlft+1),rgt=rgt-(vrgt-vlft+1) where lft>vrgt and rgt<=vsrgt;
		update nav_bar set lft=lft+(vsrgt-vrgt),rgt=rgt+(vsrgt-vrgt) where bid in (select bid from _temp_movenode);
    end if ;
		IF t_error = 1 THEN 
            ROLLBACK;    
        ELSE 
            COMMIT;    
        END IF;
    end tscrwz;
    drop table _temp_movenode;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `recnode` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `recnode`(ysid int,mbid int, rname varchar(50) CHARSET utf8)
begin
  declare t_error,rgt int DEFAULT 0;  
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET t_error=1; 
    if (select count(nav.bid) from nav_bar nav where nav.bid=mbid)=1 then
         SET SQL_SAFE_UPDATES = 0;
        START TRANSACTION;    
        set rgt =(select nav.rgt from nav_bar nav where nav.bid=mbid) ;
         update nav_bar nav set nav.rgt=nav.rgt+2 where nav.rgt>=rgt; 
		 update nav_bar nav set nav.lft=nav.lft+2 where nav.lft>=rgt;
         
		insert into nav_bar(bid,rname,lft,rgt) values (ysid,rname,rgt,rgt+1);
          IF t_error = 1 THEN    
            ROLLBACK;    
        ELSE    
            COMMIT;    
        END IF;
    end if;
    SET SQL_SAFE_UPDATES = 1;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `updwNode` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `updwNode`(ysid int,mbid int)
begin
  DECLARE vrgt,vlft,t_error,vslft,vsrgt,vslevel,vlevel,allmovenode,m int DEFAULT 0;
  DECLARE namestr varchar(60) char set utf8;
  #DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET t_error=1;
  select lft,rgt,CountLayer(ysid) into vlft,vrgt,vlevel from nav_bar where bid=ysid;
  select lft,rgt ,CountLayer(mbid) into vslft,vsrgt,vslevel from nav_bar where bid=mbid;

tscrwz :begin
   if vlft<1 or vslft<1 or vrgt<vlft or vsrgt<vslft or vlevel<vslevel then leave tscrwz; end if;
	drop table if exists _temp_updwnode;
	CREATE TEMPORARY TABLE _temp_updwnode (i int(11),bid int(11),lft int(11),rgt int(11),level int(11),rname varchar(50),primary key(bid)) DEFAULT CHARACTER SET = utf8;
    #SET SQL_SAFE_UPDATES = 0;
	START TRANSACTION;
    insert into _temp_updwnode(i,bid,lft,rgt,level,rname) SELECT (@i:=@i+1) i,bid,lft,rgt,CountLayer(bid) level,rname FROM nav_bar, (SELECT @i:=0) as i where  lft>=vlft and rgt<=vrgt order by lft asc;
	set allmovenode = (select count(bid) from _temp_updwnode);
    #所有待移动节点数目大于0且与原始节点的总数相等
if allmovenode>0 and(((vrgt+1)-vlft)/2= allmovenode) then
		call delSubnode(ysid);
        (select rname into namestr from _temp_updwnode where i=1);
        call recnode(ysid,mbid,namestr);
        set m=1;
        #重新赋值存储父节点值
        set vlft=mbid;
        while  m<allmovenode do
			set m=m+1;
            select bid,level,rname into ysid,vlevel,namestr from _temp_updwnode where i=m;
            select bid,level into mbid,vslevel from _temp_updwnode where i=m-1;
            #不是上一条记录(m-1)的子类因此目标id为以前的id
            if vlevel=vslevel then
				set mbid=vlft;
            end if;
            select ysid;
            select mbid;
			select namestr;
			call recnode(ysid,mbid,namestr);
            set vlft=mbid;
        end while;
    end if ;
    end tscrwz;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `up_dw_Node` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `up_dw_Node`(ysid int,mbid int,inout msg int)
begin
  DECLARE vrgt,vlft,t_error,vslft,vsrgt,vslevel,vlevel int DEFAULT 0;
  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET t_error=1;
  select lft,rgt,CountLayer(ysid) into vlft,vrgt,vlevel from nav_bar where bid=ysid;
  select lft,rgt ,CountLayer(mbid) into vslft,vsrgt,vslevel from nav_bar where bid=mbid;
    #原始节点占用数字数目:(vrgt-vlft+1)
tscrwz :begin
   if vlft<1 or vslft<1 or vrgt<vlft or vsrgt<vslft then leave tscrwz; end if;
    #主页不能有子节点，也不能移动！主页.lft=2
	if vlft=2 or vslft=2 then leave tscrwz; end if;
   #不能平移节点
	if vlft>vslft and vrgt<vsrgt and abs(vslevel-vlevel)=1 then leave tscrwz; end if;
    #父节点不能放入子节点
	if vlft<vslft and vrgt>vsrgt then leave tscrwz; end if ; 
	drop table if exists _temp_updwnode;
	CREATE TEMPORARY TABLE _temp_updwnode (bid int(11),lft int(11),rgt int(11),primary key(bid));
	#1、目标节点扩容
	#2、原始节点及其子节点左右值
    #原始节点与目标节点间隔数字数目(vlft-vsrgt)
    SET SQL_SAFE_UPDATES = 0;
	START TRANSACTION;
    #1、跨越父节左值设置 2、跨越父节右值设置、3目标节点扩容4、原始节点节点缩小
    #向上移动
    if vlft>vslft and vrgt>vsrgt  then
		insert into _temp_updwnode(bid,lft,rgt) values(mbid,vslft,vsrgt+(vrgt-vlft+1));
		insert into _temp_updwnode(bid,lft,rgt) select bid,lft-(vlft-vsrgt) ,rgt-(vlft-vsrgt)  from nav_bar where lft>=vlft and rgt<=vrgt;
        update nav_bar set lft=lft+(vrgt-vlft+1) where lft>vsrgt and lft<vlft;
        update nav_bar set rgt=rgt+(vrgt-vlft+1) where rgt>vsrgt and rgt<vlft;
		#update nav_bar set rgt=rgt+(vrgt-vlft+1) where bid=mbid;
		update nav_bar inner join(select bid,lft,rgt from _temp_updwnode) c on nav_bar.bid = c.bid set nav_bar.lft = c.lft,nav_bar.rgt = c.rgt;    
		set msg=6;
    end if;
      #向下移动
    if vlft<vslft  and vrgt<vsrgt then
		insert into _temp_updwnode(bid,lft,rgt) values(mbid,vslft-(vrgt-vlft+1),vsrgt);
		insert into _temp_updwnode(bid,lft,rgt) select bid,lft+(vsrgt-vrgt-1) ,rgt+(vsrgt-vrgt-1)  from nav_bar where lft>=vlft and rgt<=vrgt;
		update nav_bar set lft=lft-(vrgt-vlft+1) where lft>vrgt and lft<vsrgt;
        update nav_bar set rgt=rgt-(vrgt-vlft+1) where rgt>vrgt and rgt<vsrgt;
		#update nav_bar set lft=lft-(vrgt-vlft+1) where bid=mbid;
		update nav_bar inner join(select bid,lft,rgt from _temp_updwnode) c on nav_bar.bid = c.bid set nav_bar.lft = c.lft,nav_bar.rgt = c.rgt;    
		set msg=9;
    end if;
    # 在父节点内提升到vslevel+1层
    if vlft>vslft and vrgt<vsrgt and abs(vslevel-vlevel)<>1 then
		insert into _temp_updwnode(bid,lft,rgt) select bid,lft+(vsrgt-vrgt-1) ,rgt+(vsrgt-vrgt-1)  from nav_bar where lft>=vlft and rgt<=vrgt;
		update nav_bar set lft=lft-(vrgt-vlft+1) where lft>vrgt and lft<vsrgt;
        update nav_bar set rgt=rgt-(vrgt-vlft+1) where rgt>vrgt and rgt<vsrgt;
		#update nav_bar set lft=lft-(vrgt-vlft+1) where bid=mbid;
		update nav_bar inner join(select bid,lft,rgt from _temp_updwnode) c on nav_bar.bid = c.bid set nav_bar.lft = c.lft,nav_bar.rgt = c.rgt;    
		set msg=2;
    end if;
		IF t_error = 1 THEN 
			set msg =-1 ;
            ROLLBACK;
        ELSE 
			set msg =666 ;
            COMMIT;    
        END IF;
    end tscrwz;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-11-21 11:36:14
