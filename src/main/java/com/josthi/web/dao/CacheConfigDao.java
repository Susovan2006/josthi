package com.josthi.web.dao ;
import java.util.List;
import com.josthi.web.po.CacheConfigPO ;



public interface CacheConfigDao {

	List<CacheConfigPO> getConfigData();

	List<String> getScreenNames();

}
