curl -X POST localhost:8080/api/ggrecord -H 'Content-type:application/json' -d '
{
  "timestampAsString" : "2021-03-14 10:16:50.000000",
  "sqlType" : "UPDATE",
  "tableName" : "SIEBEL.S_PARTY",
  "xid" : "93.32.1267816",
  "csn" : "2305538390074",
  "operationSeqNo" : 1,
  "isLast" : false,
  "hasLobValue" : false,
  "ggcolumns" : [ {
    "columnName" : "ROW_ID",
    "jdbcColumnType" : "VARCHAR",
    "columnLength" : 15,
    "isChanged" : false,
    "isKeyCol" : true,
    "beforeValue" : "4-186K38MM",
    "afterValue" : "4-186K38MM"
  }, {
    "columnName" : "LAST_UPD",
    "jdbcColumnType" : "TIMESTAMP",
    "columnLength" : 19,
    "isChanged" : true,
    "isKeyCol" : false,
    "beforeValue" : "2021-03-14 10:11:46",
    "afterValue" : "2021-03-14 10:16:50"
  }, {
    "columnName" : "MODIFICATION_NUM",
    "jdbcColumnType" : "NUMERIC",
    "columnLength" : 20,
    "isChanged" : true,
    "isKeyCol" : false,
    "beforeValue" : "6201598",
    "afterValue" : "6201599"
  }, {
    "columnName" : "DB_LAST_UPD",
    "jdbcColumnType" : "TIMESTAMP",
    "columnLength" : 19,
    "isChanged" : true,
    "isKeyCol" : false,
    "beforeValue" : "2021-03-14 10:11:46",
    "afterValue" : "2021-03-14 10:16:50"
  } ]
}'
