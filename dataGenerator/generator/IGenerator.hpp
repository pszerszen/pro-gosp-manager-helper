#include "../data/Store.hpp"
#include "../data/Supply.hpp"
#include "../data/Transaction.hpp"
#include "../data/productState.hpp"
#include "../modelGenerator/IModeGenerator.hpp"

Store stores[MAX_NUM_OF_SHOPS];
Store stores[MAX_NUM_OF_SHOPS];
Store stores[MAX_NUM_OF_SHOPS];
Store stores[MAX_NUM_OF_SHOPS];
Store stores[MAX_NUM_OF_SHOPS];


template<typename T> 
class IGenerator
{
public:
	iGenerator(){}
	T generateRecord();
};

template<typename T> 
class universalGenerator : public iGenerator
{

T generateRecord()
{
	return generator->createRecord();
}

private:
iModelGenerator* generator;


}