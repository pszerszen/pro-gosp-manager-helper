#ifndef Users_roles_HPP
#define Users_roles_HPP

#include <string>
#include <boost/fusion/adapted/struct.hpp>
#include <boost/fusion/include/for_each.hpp>
#include <boost/phoenix/phoenix.hpp>



struct Usres_roles
{
	unsigned id_user;
	unsigned id_role;
};

BOOST_FUSION_ADAPT_STRUCT(User_role, (unsigned,id_user)(unsigned,id_role))
#endif // Users_roles_HPP
