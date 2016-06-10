package zttc.itat.user.dao;

import zttc.itat.user.model.User;

public interface IUserDao {
	public void add(User user);
	public User loadByUsername(String username);
}
