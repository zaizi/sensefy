/*
  This is a basic skeleton JavaScript update processor.

  In order for this to be executed, it must be properly wired into solrconfig.xml; by default it is commented out in
  the example solrconfig.xml and must be uncommented to be enabled.

  See http://wiki.apache.org/solr/ScriptUpdateProcessor for more details.
*/

function processAdd(cmd) {

  doc = cmd.solrDoc;  // org.apache.solr.common.SolrInputDocument
  id = doc.getFieldValue("id");
  logger.info("update-script#processAdd: id=" + id);

  
  var date_time_original = doc.getFieldValue("date_time_original");
  logger.info("date value before : " + date_time_original);
  dateParse(date_time_original); 

// Set a field value:
//  doc.setField("foo_s", "whatever");

// Get a configuration parameter:
//  config_param = params.get('config_param');  // "params" only exists if processor configured with <lst name="params">

// Get a request parameter:
// some_param = req.getParams().get("some_param")

// Add a field of field names that match a pattern:
//   - Potentially useful to determine the fields/attributes represented in a result set, via faceting on field_name_ss
//  field_names = doc.getFieldNames().toArray();
//  for(i=0; i < field_names.length; i++) {
//    field_name = field_names[i];
//    if (/attr_.*/.test(field_name)) { doc.addField("attribute_ss", field_names[i]); }
//  }

}

function processDelete(cmd) {
  // no-op
}

function processMergeIndexes(cmd) {
  // no-op
}

function processCommit(cmd) {
  // no-op
}

function processRollback(cmd) {
  // no-op
}

function finish() {
  // no-op
}

////utilityfunctions
function dateParse(date_time_original){
    var pattern = /^(\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2}):(\d{2})$/;

    if(date_time_original != null &&  date_time_original != undefined && pattern.test(date_time_original))
    {     
        logger.info("Matched date pattern");   
        var m = date_time_original.match(pattern);
        var d1 = new Date(m[1],m[2]-1,m[3],m[4], m[5], m[6]); //m[1],m[2]-1,m[3],m[4], m[5], m[6]
        //logger.info("values :{}, {} ,{}, {}, {}, {}, {}",m,m[1],m[2]-1,m[3],m[4], m[5], m[6]);
        logger.info("value " + d1.toISOString());
        doc.setField("date_time_original",d1.toISOString()); 
        doc.setField("ds_creation_date",d1.toISOString());        
    }
}
