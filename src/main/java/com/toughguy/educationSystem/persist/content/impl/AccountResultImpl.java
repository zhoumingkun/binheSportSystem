package com.toughguy.educationSystem.persist.content.impl;
import org.springframework.stereotype.Repository;

import com.toughguy.educationSystem.model.content.AccountResult;
import com.toughguy.educationSystem.persist.content.prototype.IAccountResultDao;
import com.toughguy.educationSystem.persist.impl.GenericDaoImpl;
/**
 * 用户与结果Dao实现类
 * @author zmk
 *
 */
@Repository
public class AccountResultImpl extends GenericDaoImpl<AccountResult, Integer> implements IAccountResultDao{
	

}