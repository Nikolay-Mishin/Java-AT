import { log, error } from 'console';

const laboriousnesses = [{ laboriousness: { value: 1, pub_module_link: "link" } }];
const { laboriousness: { value, pub_module_link: link } } = laboriousnesses[0];
//const link = laboriousness.pub_module_link;
log(value);
log(link);
