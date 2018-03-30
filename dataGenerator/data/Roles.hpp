#ifndef Roles_HPP
#include Roles_HPP

#include <string>
#include <boost/fusion/adapted/struct.hpp>
#include <boost/fusion/include/for_each.hpp>
#include <boost/phoenix/phoenix.hpp>



struct Roles
{
	unsigned id;
	std::string role_name;
};

BOOST_FUSION_ADAPT_STRUCT(Roles, (unsigned,id)(std::string,role_name))
#ifend // Roles_HPP
