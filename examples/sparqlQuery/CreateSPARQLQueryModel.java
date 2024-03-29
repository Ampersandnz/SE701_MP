package sparqlQuery;

import pdstore.GUID;
import pdstore.PDStore;
import pdstore.dal.PDGen;

public class CreateSPARQLQueryModel {

	public static void main(String[] args) {

		PDStore store = new PDStore("sparqlQuery");
		GUID transaction = store.begin();

		// create a new model, with a new GUID (generated by GUIDGen)
		GUID model = new GUID("5b60e04043b111e08850842b2b9af4fd");
		store.createModel(transaction, model, "sparqlQueryModel");

		// Distinct, union, limit, offset, order by to be added later in
		// abstract
		// model

		// creating variable as a type
		store.createType(transaction, model, PDStore.SPARQL_VARIABLE_TYPEID,
				"SPARQLVariable");

		// create change type and relation
		store.createType(transaction, model, PDStore.CHANGE_TYPEID, "Change");

		store.createRelation(transaction, PDStore.CHANGE_TYPEID, null,
				"transaction", PDStore.CHANGE_TRANSACTION_ROLEID,
				PDStore.TRANSACTION_TYPEID);
		store.createRelation(transaction, PDStore.CHANGE_TYPEID, null,
				"change type", PDStore.CHANGE_CHANGETYPE_ROLEID,
				PDStore.STRING_TYPEID);
		store.createRelation(transaction, PDStore.CHANGE_TYPEID, null,
				"instance1", PDStore.CHANGE_INSTANCE1_ROLEID,
				PDStore.OBJECT_TYPEID);
		store.createRelation(transaction, PDStore.CHANGE_TYPEID, null, "role2",
				PDStore.CHANGE_ROLE2_ROLEID, PDStore.ROLE_TYPEID);
		store.createRelation(transaction, PDStore.CHANGE_TYPEID, null,
				"instance2", PDStore.CHANGE_INSTANCE2_ROLEID,
				PDStore.OBJECT_TYPEID);

		// create the new complex type Where and relation
		store.createType(transaction, model, PDStore.WHERE_TYPEID,
				"SPARQLQueryWhere");

		store.createRelation(transaction, PDStore.WHERE_TYPEID, null,
				"whereChange", PDStore.CHANGE_CHANGETYPE_ROLEID,
				PDStore.CHANGE_TYPEID);

		// create the new complex type Filter and relation
		store.createType(transaction, model, PDStore.FILTER_TYPEID,
				"SPARQLQueryFilter");

		// All different kinds of filter can be sub types of filter
		// Creating sub filter types
		store.createType(transaction, model, PDStore.FILTER_EQUAL_TYPEID,
				"SPARQLQueryFilterEqual");
		store.createType(transaction, model, PDStore.FILTER_NOTEQUAL_TYPEID,
				"SPARQLQueryFilterNotEqual");
		store.createType(transaction, model, PDStore.FILTER_LESSTHAN_TYPEID,
				"SPARQLQueryFilterLessThan");
		store.createType(transaction, model, PDStore.FILTER_GREATERTHAN_TYPEID,
				"SPARQLQueryFilterGreaterThan");
		store.createType(transaction, model, PDStore.FILTER_AND_TYPEID,
				"SPARQLQueryFilterAND");
		store.createType(transaction, model, PDStore.FILTER_OR_TYPEID,
				"SPARQLQueryFilterOR");

		// Assigning filter as a super type for sub filter types
		// TODO: is this enough to create a super and sub type relation ?
		store.createRelation(transaction, PDStore.FILTER_EQUAL_TYPEID,
				"subtype", "supertype", PDStore.HAS_SUPERTYPE_ROLEID,
				PDStore.FILTER_TYPEID);
		store.createRelation(transaction, PDStore.FILTER_NOTEQUAL_TYPEID,
				"subtype", "supertype", PDStore.HAS_SUPERTYPE_ROLEID,
				PDStore.FILTER_TYPEID);
		store.createRelation(transaction, PDStore.FILTER_LESSTHAN_TYPEID,
				"subtype", "supertype", PDStore.HAS_SUPERTYPE_ROLEID,
				PDStore.FILTER_TYPEID);
		store.createRelation(transaction, PDStore.FILTER_GREATERTHAN_TYPEID,
				"subtype", "supertype", PDStore.HAS_SUPERTYPE_ROLEID,
				PDStore.FILTER_TYPEID);
		store.createRelation(transaction, PDStore.FILTER_AND_TYPEID, "subtype",
				"supertype", PDStore.HAS_SUPERTYPE_ROLEID,
				PDStore.FILTER_TYPEID);
		store.createRelation(transaction, PDStore.FILTER_OR_TYPEID, "subtype",
				"supertype", PDStore.HAS_SUPERTYPE_ROLEID,
				PDStore.FILTER_TYPEID);

		// AND and OR filters will not work with variables or constants directly
		store.createRelation(transaction, PDStore.FILTER_AND_TYPEID,
				"SPARQLQueryFilterAnd1", "filter1", PDStore.FILTER_AND_ROLEID1,
				PDStore.FILTER_TYPEID);
		store.createRelation(transaction, PDStore.FILTER_AND_TYPEID,
				"SPARQLQueryFilterAnd2", "filter2", PDStore.FILTER_AND_ROLEID2,
				PDStore.FILTER_TYPEID);

		store.createRelation(transaction, PDStore.FILTER_OR_TYPEID,
				"SPARQLQueryFilterOr1", "filter1", PDStore.FILTER_OR_ROLEID1,
				PDStore.FILTER_TYPEID);
		store.createRelation(transaction, PDStore.FILTER_OR_TYPEID,
				"SPARQLQueryFilterOr2", "filter2", PDStore.FILTER_OR_ROLEID2,
				PDStore.FILTER_TYPEID);

		// Adding variable and constant relation to filters
		// TODO: Can the constant be a object type id ?
		store.createRelation(transaction, PDStore.FILTER_EQUAL_TYPEID,
				"SPARQLQueryFilterEqualVariable", "filterVariable",
				PDStore.VARIABLE_ROLEID, PDStore.SPARQL_VARIABLE_TYPEID);

		store.createRelation(transaction, PDStore.FILTER_EQUAL_TYPEID,
				"SPARQLQueryFilterEqualConstant", "filterConstant",
				PDStore.CONSTANT_ROLEID, PDStore.STRING_TYPEID);

		store.createRelation(transaction, PDStore.FILTER_NOTEQUAL_TYPEID,
				"SPARQLQueryFilterNotEqual", "filterVariable",
				PDStore.VARIABLE_ROLEID, PDStore.SPARQL_VARIABLE_TYPEID);
		store.createRelation(transaction, PDStore.FILTER_NOTEQUAL_TYPEID,
				"SPARQLQueryFilterNotEqual", "filterConstant",
				PDStore.CONSTANT_ROLEID, PDStore.STRING_TYPEID);

		store.createRelation(transaction, PDStore.FILTER_LESSTHAN_TYPEID,
				"SPARQLQueryFilterLessThan", "filterVariable",
				PDStore.VARIABLE_ROLEID, PDStore.SPARQL_VARIABLE_TYPEID);
		store.createRelation(transaction, PDStore.FILTER_LESSTHAN_TYPEID,
				"SPARQLQueryFilterLessThan", "filterConstant",
				PDStore.CONSTANT_ROLEID, PDStore.STRING_TYPEID);

		store.createRelation(transaction, PDStore.FILTER_GREATERTHAN_TYPEID,
				"SPARQLQueryFilterGreaterThan", "filterVariable",
				PDStore.VARIABLE_ROLEID, PDStore.SPARQL_VARIABLE_TYPEID);
		store.createRelation(transaction, PDStore.FILTER_GREATERTHAN_TYPEID,
				"SPARQLQueryFilterGreaterThan", "filterConstant",
				PDStore.CONSTANT_ROLEID, PDStore.STRING_TYPEID);

		// create the new complex type query and relation
		store.createType(transaction, model, PDStore.SPARQL_QUERY_TYPEID,
				"SPARQLQuery");
		store.createRelation(transaction, PDStore.SPARQL_QUERY_TYPEID, null,
				"where", PDStore.WHERE_TUPLE_ROLEID, PDStore.WHERE_TYPEID);
		store.createRelation(transaction, PDStore.SPARQL_QUERY_TYPEID, null,
				"filter", PDStore.FILTER_ROLEID, PDStore.FILTER_TYPEID);
		store.createRelation(transaction, PDStore.SPARQL_QUERY_TYPEID, null,
				"subquery", PDStore.SUBQUERY_ROLEID,
				PDStore.SPARQL_QUERY_TYPEID);
		store.createRelation(transaction, PDStore.SPARQL_QUERY_TYPEID, null,
				"optional", PDStore.OPTIONAL_ROLEID,
				PDStore.SPARQL_QUERY_TYPEID);

		store.commit(transaction);

		// generate DAL into package "sparqlQuery.dal" in folder "examples"
		PDGen.generateModel(store, "sparqlQueryModel", "examples",
				"sparqlQuery.dal");
	}
}
