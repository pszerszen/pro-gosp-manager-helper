#include <string>
#include <sstream>
#include <iostream>
#include <fstream>
#include <boost/fusion/adapted/struct.hpp>
#include <boost/fusion/include/for_each.hpp>
#include <boost/phoenix/phoenix.hpp>

class iDataWritter
{
public:
  iDataWritter(){};
  virtual void writeToFile(std::ofstream& output) = 0;
};

template<typename T>
class universalWritter : public iDataWritter
{
public:

	universalWritter():obj(NULL){}

	void placeObjInStringStream(std::stringstream& ss, std::string& st)
	{
		boost::fusion::for_each(*obj, ss << boost::phoenix::arg_names::arg1 << " , ");//formatowanie
		std::string myString = ss.str();
		st = myString.substr(0, myString.size()-2);
	}

	virtual void writeToFile(std::ofstream& output)
	{
		if(obj != NULL)
		{
			std::stringstream ss;
			std::string str;
			placeObjInStringStream(ss,str);
			str.insert (str.end(),1,'\n');
			output << str;
		}
	}

	void changeObj(T* newObj)
	{
		obj = newObj;
	}

	~universalWritter()
	{
		obj = NULL;
	}
private:
	T* obj;
};