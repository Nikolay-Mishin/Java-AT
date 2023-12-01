import { log } from 'console';
import { env, cwd, argv as $argv } from 'process';

const { INIT_CWD: root, HOMEDRIVE } = env,
	argv = $argv.slice(2),
	parseArgs = (argList, sep = /^\-+/) => {
		let args = {}, opt, thisOpt, curOpt;
		argList.forEach(arg => {
			thisOpt = arg.trim();
			opt = thisOpt.replace(sep, '');
			if (thisOpt === opt) {
				if (curOpt) args[curOpt] = opt; // argument value
				curOpt = null;
			}
			else args[curOpt = opt] = true; // argument name
		});
		return args;
	},
	args = (argList => parseArgs(argList))(argv);

log(env);
log(root);
log(HOMEDRIVE);
log(args);
