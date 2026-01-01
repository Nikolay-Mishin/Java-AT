import { log } from 'console';
import { argv, env } from 'process';
import { resolve } from 'path';
import { getFiles, getVideos, appendFiles, isArr, isNull, addPath, getEnv, rename, loadEnv, args } from './baseHelpers.js';
import { FFreport, FFlog, setMetrics, ffreportDir, execute, metrics, reportFile, logFile, test } from './ffmpeg.config.js';
import { ffColor, ffError, getFormat, getReportInfo, mkvAddTags, mkvExtAll, mkvExtAttList, mkvExtTracks, mkvMerge, mkvReport, parseReports, primaries, space, trc } from './opts.js';
import { exif, matrix, mi, size } from './mi.js';
import { check_sei, check_v, copyAtt, copyAttExt, copy_v, ffmpeg, getMetrics, scan } from './core.js';
import { setParams } from './params.js';
import { envRoot, root } from './env.config.js';
import { runScript } from './helpers.js';

const _testScripts = async (n = 0, script, args) => {
    if (n === 0) await runScript('test', '-c_range=false');
    if (n > 0 && script) {
        const _args = !args ? '' : ` ${args}`;

        if (n <= 3) {
            await runScript(script, `-c_range=true -vf_range=true -scale_space=true -vf_space=true -space=true -n=0${_args}`);
            await runScript(script, `-c_range=true -vf_range=true -scale_space=false -vf_space=true -space=true -n=1${_args}`);
            await runScript(script, `-c_range=true -vf_range=false -scale_space=false -vf_space=true -space=true -n=2${_args}`);
            await runScript(script, `-c_range=true -vf_range=true -scale_space=true -vf_space=false -space=true -n=3${_args}`);
            await runScript(script, `-c_range=true -vf_range=true -scale_space=false -vf_space=false -space=true -n=4${_args}`);
            if (n >= 2) await runScript(script, `-c_range=true -vf_range=false -scale_space=false -vf_space=false -space=true -n=5${_args}`);

            if (n == 3 && script === 'metrics') await testScripts(1, `-out_scale_m=false`);
        }

        if (n === 4) {
            if (script !== 'metrics') {
                await runScript(script, `-c_range=false -vf_range=false -scale_space=false -vf_space=false -space=false -n=6${_args}`);
                await runScript(script, `-c_range=true -vf_range=false -scale_space=false -vf_space=false -space=false -n=7${_args}`);
                await runScript(script, `-c_range=false -vf_range=false -scale_space=false -vf_space=false -space=true -n=8${_args}`);
            }
            await runScript(script, `-c_range=true -vf_range=true -scale_space=true -vf_space=true -space=true -vf_flags=lanczos -n=9${_args}`);
            if (script === 'metrics') {
                await runScript(script, `-c_range=true -vf_range=true -scale_space=true -vf_space=true -space=true -vf_flags=lanczos -out_scale_m=false -n=9${_args}`);
            }
        }

        if (n == 5 || n == 6) {
            await runScript(script, `-c_range=true -vf_range=true -scale_space=true -vf_space=true -space=true -vf_sar=true -vf_dar=false -m_sar=true -m_dar=false -n=10${_args}`);
            await runScript(script, `-c_range=true -vf_range=true -scale_space=true -vf_space=true -space=true -vf_sar=true -vf_dar=true -m_sar=true -m_dar=true -n=11${_args}`);
            await runScript(script, `-c_range=false -vf_range=true -scale_space=true -vf_space=false -space=false -vf_sar=true -vf_dar=true -m_sar=true -m_dar=true -n=12${_args}`);
            await runScript(script, `-c_range=false -vf_range=true -scale_space=true -vf_space=false -space=false -vf_sar=true -vf_dar=false -m_sar=true -m_dar=false -n=13${_args}`);

            if (n == 6 && script === 'metrics') await testScripts(5, `-out_scale_m=false`);
        }

        if (n == 7) {
            await runScript(script, `-c_range=false -vf_range=false -scale_space=false -vf_space=false -space=false -vf_sar=false -vf_dar=false -n=14${_args}`);
            await runScript(script, `-c_range=false -vf_range=false -scale_space=false -vf_space=true -space=false -vf_sar=false -vf_dar=false -n=15${_args}`);

            await runScript(script, `-c_range=false -vf_range=false -scale_space=false -vf_space=false -space=true -p_space=false -vf_sar=false -vf_dar=false -n=16${_args}`);
            await runScript(script, `-c_range=false -vf_range=false -scale_space=false -vf_space=false -space=true -p_space=true -vf_sar=false -vf_dar=false -n=17${_args}`);

            await runScript(script, `-c_range=true -c_space=false -vf_range=false -scale_space=false -vf_space=false -space=false -vf_sar=false -vf_dar=false -n=18${_args}`);
            await runScript(script, `-c_range=true -c_space=true -vf_range=false -scale_space=false -vf_space=false -space=false -vf_sar=false -vf_dar=false -n=19${_args}`);

            await runScript(script, `-c_range=false -vf_range=false -scale_space=false -vf_space=false -space=false -vf_sar=false -vf_dar=true -n=20${_args}`);
            await runScript(script, `-c_range=false -vf_range=false -scale_space=false -vf_space=false -space=false -vf_sar=true -vf_dar=false -n=21${_args}`);

            await runScript(script, `-c_range=false -vf_range=false -scale_space=false -vf_space=false -space=false -vf_sar=false -vf_dar=false -n=22${_args}`);
            await runScript(script, `-c_range=false -vf_range=true -scale_space=false -vf_space=false -space=false -vf_sar=false -vf_dar=false -n=23${_args}`);
        }

        if (n == 8) {
            await runScript(script, `-c_range=false -vf_range=true -scale_space=true -vf_space=false -space=false -vf_sar=false -vf_dar=false -n=24${_args}`);
            await runScript(script, `-c_range=false -vf_range=true -scale_space=true -vf_space=true -space=false -vf_sar=false -vf_dar=false -n=25${_args}`);

            await runScript(script, `-c_range=false -vf_range=true -scale_space=true -vf_space=true -space=true -p_space=false -vf_sar=false -vf_dar=false -n=26${_args}`);
            await runScript(script, `-c_range=false -vf_range=true -scale_space=true -vf_space=true -space=true -p_space=true -vf_sar=false -vf_dar=false -n=27${_args}`);

            await runScript(script, `-c_range=true -c_space=false -vf_range=true -scale_space=true -vf_space=true -space=false -vf_sar=false -vf_dar=false -n=28${_args}`);
            await runScript(script, `-c_range=true -c_space=true -vf_range=true -scale_space=true -vf_space=true -space=false -vf_sar=false -vf_dar=false -n=29${_args}`);

            await runScript(script, `-c_range=false -vf_range=true -scale_space=true -vf_space=true -space=false -vf_sar=false -vf_dar=true -n=30${_args}`);
            await runScript(script, `-c_range=false -vf_range=true -scale_space=true -vf_space=true -space=false -vf_sar=true -vf_dar=false -n=31${_args}`);

            await runScript(script, `-c_range=false -vf_range=true -scale_space=true -vf_space=true -space=false -vf_sar=false -vf_dar=false -n=32${_args}`);
        }

        if (n == 9) {
            await runScript(script, `-c_range=true -vf_range=true -scale_space=true -vf_space=true -space=true -vf_sar=false -vf_dar=false -n=42${_args}`);
            await runScript(script, `-c_range=true -vf_range=true -scale_space=true -vf_space=true -space=false -vf_sar=false -vf_dar=false -n=43${_args}`);
            await runScript(script, `-c_range=false -vf_range=true -scale_space=true -vf_space=true -space=true -vf_sar=false -vf_dar=false -n=44${_args}`);
            await runScript(script, `-c_range=true -vf_range=false -scale_space=false -vf_space=true -space=true -vf_sar=false -vf_dar=false -n=45${_args}`);
        }

        if (n == 10) {
            await runScript(script, `-c_range=false -vf_range=false -scale_space=false -vf_space=false -space=false -vf_sar=false -vf_dar=false -n=46${_args}`);
        }
    }
};

export const
    testScripts = async (n = 0, args) => await _testScripts(n, 'start', args),
    testScriptsMetrics = async (n = 0, args) => await _testScripts(n, 'metrics', args);

export const testArgs = () => {
    log(argv);
    log(args);
};

export const testEnv = () => {
    log(root);

    addPath('lib/ffmpeg/bin');

    log(env);
    log(env.Path);

    log(getEnv('ffmpeg'));
};

export const testFormat = () => {
    log(getFormat('pix_fmts=yuv420p10le'));
    log(getFormat('pix_fmts=yuv420p10'));
    log(getFormat('pix_fmts=yuv420p'));
};

export const testRename = async () => {
    const replace = { '(1)': '(1-5)', '(2)': '(2-10)', '(3)': '(3-7)', '(2-5)': '(5-11)', '(2-6)': '(5-12)', '(2-7)': '(5-13)', '(2-8)': '(5-14)', '(3-9)': '(6-25)' };
    await rename('D:\\Convert\\frames\\2', replace);
};

const testFF = async (pathList, fn, env = false) => {
    if (env) await loadEnv(envRoot, false);
    for await (const p of pathList) {
        const out = await fn(p);
        log(out);
    }
};

export const
    testColor = async (pathList) => await testFF(pathList, ffColor),
    testError = async (pathList) => await testFF(pathList, ffError),
    testDirExist = async (dir = "F:\\Convert\\test") => await scan(dir),
    testFileExist = async (f = "F:\\Convert\\files\\test.mkv") => await ffmpeg(f);

export const testFFmpeg = async (n = 0) => {
    // 0 - Default
    // 01 - Max
    // 02 - Max + Отклонение (разрешить)
    // 03 - MFAA (off)
    // 04 - FXAA (off)
    // 05 - Прозрачность (off)
    // 06 - Сглаживание (off)
    // 07 - Сглаживание (приложение)
    // 08 - Анизатропная (off)
    // 09 - Анизатропная (приложение)
    // 10 - Отклонение (разрешить)
    // 11 - Гамма (off) + Отклонение (привязка)
    // 12 - All Off + Отклонение (разрешить)

    await ffmpeg("G:\\Convert\\files\\FBS NF 124 (1080P AVC).mp4", `_HEVC(${n})`, true);
    await ffmpeg("G:\\Convert\\files\\Nanatsu_no_Maken_ga_Shihai_suru_[02]_[AniLibria_TV]_[WEBRip_1080p].mkv", `_HEVC(${n})`, true);
    await ffmpeg("G:\\Convert\\files\\[DC] Make Heroine ga Oosugiru - 07_Telegram.mp4", `_HEVC(${n})`, true);
    await ffmpeg("G:\\Convert\\files\\[JamClub] Tsue to Tsurugi no Wistoria - 11 [1080p].mp4", `_HEVC(${n})`, true);
    await ffmpeg("G:\\Аниме\\Новое\\Макен-ки\\ТВ-1\\[AniDub] Maken-ki! [01] [BDrip1080p x264 FLAC] [Ancord].mkv", `_HEVC(${n})`, true);
};

export const testMI = async () => {
    log(await mi("F:\\Аниме\\Онгоинги\\!_38_Весна_2025\\Дьявол может плакать\\Devil_May_Cry_[01]_[AniLibria]_[WEBRip_1080p_HEVC].mkv"));
    log(await mi("F:\\Аниме\\Онгоинги\\!_38_Весна_2025\\Дьявол может плакать\\Devil.May.Cry.s01e01.WEBRip.x264.Rus.RuDub.tv.mp4"));
    log(await mi("F:\\Аниме\\[Фильмы]\\[Фильм] Алита - Боевой ангел\\Alita Battle Angel 2019 WEBRip 1080p-LQ.mkv"));
    log(await mi("F:\\Convert\\files\\[DC] Make Heroine ga Oosugiru - 07_Telegram.mp4"));
};

export const testCheck = async path => {
    await check_v(path);
    await check_sei(path);
};

export const testParams = async (files, params) => {
    for await (const f of files) {
        let p_i = 0;
        for await (const p of params) {
            //p_i++;
            setParams(p);
            await ffmpeg(f, `_HEVC${p_i === 0 ? '' : '(' + (p_i !== 0 || p_i < 10 ? '0' : '') + p_i + ')'}`, true);
        }
    }
};

export const testParams2 = async (iList, skip = 0, ...p) => {
    let { i, rec } = testParams2;
    //log(p);
    //log(i);
    for await (const _p of p) {
        if (i < skip) { testParams2.i = ++i; continue; }
        const prefix_search = [5, 9, 10, 15, 20, 22, 23, 24, 26, 32, 38, 43, 53, 59, 60, 64, 68, 71, 74, 76, 79, 88, 94, 101, 110, 116];
        const prefix_i = prefix_search.findIndex((v, _i) => i < v) + 1;
        const prefix = i < 5 ? '' : `${prefix_i === 0 ? prefix_search.length + 1 : prefix_i}-`;
        if (isArr(_p)) {
            let p_min = _p[0];
            const p_max = _p.pop();
            while (p_min++ < p_max) _p.push(p_min);
            testParams2.i = i;
            testParams2.rec = true;
            i = await testParams2(iList, skip, ..._p);
        } else {
            log(isNull(_p) || i === _p ? i : `${i}-${_p}`);
            setParams(_p);
            for await (const _i of iList) await ffmpeg(_i, `_HEVC(${isNull(_p) || i === _p ? i : `${i}-${_p}`})`, true);
            if (!rec) i++;
        }
    }
    testParams2.rec = false;
    return testParams2.i = ++i;
};
testParams2.i = 0;
testParams2.rec = false;

export const testAtt = async (i, o, a) => {
    //await copyAtt(o, i, a);
    await copyAttExt(i, o);
    //await mkvMerge(i);
    //await mkvExtAll(i);
    //await mkvExtAll(i, "a");
    //await mkvExtAttList(i, "a");
    //await mkvExtTracks(i);
};

export const testCopy = async (ext, i, i0, i1, i2, i3) => {
    //await mkvAddTags(i);
    //await copy_v(i, ext);
    //await copy_v(i);
    //await copy_v(i0);
    //await copy_v(i1);
    await copy_v(i2, ext);
    await copy_v(i3);
};

export const testMatrix = (mList) => {
    mList.forEach(m => log(matrix(m)));
    [primaries, trc, space].forEach((m, i) => log(m[matrix(mList[i])]));
};

// task queue
export const arr = async (dir, dirJ, dirDC, n = 0) => {
    //5
    const dirs = {
        "Поднятие уровня в одиночку\\ТВ-2": [
            //"JamClub_Ore_dake_Level_Up_na_Ken_Season_2_01_1080p.mp4",
            //"JamClub_Ore_dake_Level_Up_na_Ken_Season_2_03_1080p.mp4",
            //"JamClub_Ore_dake_Level_Up_na_Ken_Season_2_06_1080p.mp4",
            //"JamClub_Ore_dake_Level_Up_na_Ken_Season_2_07_1080p.mp4",
            //"JamClub_Ore_dake_Level_Up_na_Ken_Season_2_08_1080p.mp4"
        ]
    };

    //13
    const dirs0 = {
        "Меч и жезл Вистории": [
            //"JamClub_Tsue_to_Tsurugi_no_Wistoria_02_1080p.mp4",
            //"JamClub_Tsue_to_Tsurugi_no_Wistoria_03_1080p.mp4",
            //"JamClub_Tsue_to_Tsurugi_no_Wistoria_04_1080p.mp4",
            //"JamClub_Tsue_to_Tsurugi_no_Wistoria_05_1080p.mp4",
            //"JamClub_Tsue_to_Tsurugi_no_Wistoria_06_1080p.mp4",
            //"JamClub_Tsue_to_Tsurugi_no_Wistoria_07_1080p.mp4",
            //"JamClub_Tsue_to_Tsurugi_no_Wistoria_09_1080p.mp4",
            //"JamClub_Tsue_to_Tsurugi_no_Wistoria_10_1080p.mp4",
            //"JamClub_Tsue_to_Tsurugi_no_Wistoria_11_1080p.mp4",
            //"JamClub_Tsue_to_Tsurugi_no_Wistoria_12_1080p.mp4"
        ],
        "Моя сэмпай - парень": [
            //"JamClub_Senpai_wa_Otokonoko_02_1080p.mp4",
            //"JamClub_Senpai_wa_Otokonoko_03_1080p.mp4",
            //"JamClub_Senpai_wa_Otokonoko_04_1080p.mp4",
            //"JamClub_Senpai_wa_Otokonoko_05_1080p.mp4",
            //"JamClub_Senpai_wa_Otokonoko_06_1080p.mp4",
            //"JamClub_Senpai_wa_Otokonoko_08_1080p.mp4",
            //"JamClub_Senpai_wa_Otokonoko_09_1080p.mp4",
            //"JamClub_Senpai_wa_Otokonoko_10_1080p.mp4",
            //"JamClub_Senpai_wa_Otokonoko_12_1080p.mp4"
        ],
        "Почему все забыли мой мир": [
            //"JamClub_Naze_Boku_no_Sekai_o_Dare_mo_Oboeteinai_no_ka_02_1080p.mp4",
            //"JamClub_Naze_Boku_no_Sekai_o_Dare_mo_Oboeteinai_no_ka_04_1080p.mp4",
            //"JamClub_Naze_Boku_no_Sekai_o_Dare_mo_Oboeteinai_no_ka_06_1080p.mp4",
            //"JamClub_Naze_Boku_no_Sekai_o_Dare_mo_Oboeteinai_no_ka_08_1080p.mp4",
            //"JamClub_Naze_Boku_no_Sekai_o_Dare_mo_Oboeteinai_no_ka_09_1080p.mp4",
            //"JamClub_Naze_Boku_no_Sekai_o_Dare_mo_Oboeteinai_no_ka_10_1080p.mp4",
            //"JamClub_Naze_Boku_no_Sekai_o_Dare_mo_Oboeteinai_no_ka_11_1080p.mp4"
        ],
        "Псевдогарем": [
            //"JamClub_Giji_Harem_03_1080p.mp4",
            //"JamClub_Giji_Harem_04_1080p.mp4",
            //"JamClub_Giji_Harem_05_1080p.mp4",
            //"JamClub_Giji_Harem_06_1080p.mp4",
            //"JamClub_Giji_Harem_07_1080p.mp4",
            //"JamClub_Giji_Harem_08_1080p.mp4",
            //"JamClub_Giji_Harem_10_1080p.mp4",
            //"JamClub_Giji_Harem_11_1080p.mp4",
            //"JamClub_Giji_Harem_12_1080p.mp4"
        ]
    };

    //22
    const dirs1 = {
        "Аля иногда кокетничает со мной по-русски": [
            //"[DC] Roshidere - 02_Telegram.mp4",
            //"[DC] Roshidere - 03_Telegram.mp4",
            //"[DC] Roshidere - 04_Telegram.mp4",
            //"[DC] Roshidere - 05_Telegram.mp4",
            //"[DC] Roshidere - 06_Telegram.mp4",
            //"[DC] Roshidere - 07_Telegram.mp4",
            //"[DC] Roshidere - 08_Telegram.mp4",
            //"[DC] Roshidere - 09_Telegram.mp4",
            //"[DC] Roshidere - 10_Telegram.mp4",
            //"[DC] Roshidere - 11_Telegram.mp4"
        ],
        "Атри - Мои дорогие моменты": [
            //"[DC] Atri - My Dear Moments - 02_Telegram.mp4",
            //"[DC] Atri - My Dear Moments - 03_Telegram.mp4",
            //"[DC] Atri - My Dear Moments - 04_Telegram.mp4",
            //"[DC] Atri - My Dear Moments - 06_Telegram.mp4",
            //"[DC] Atri - My Dear Moments - 07_Telegram.mp4",
            //"[DC] Atri - My Dear Moments - 08_Telegram.mp4",
            //"[DC] Atri - My Dear Moments - 09_Telegram.mp4",
            //"[DC] Atri - My Dear Moments - 10_Telegram.mp4",
            //"[DC] Atri - My Dear Moments - 11_Telegram.mp4",
            //"[DC] Atri - My Dear Moments - 12_Telegram.mp4",
            //"[DC] Atri - My Dear Moments - 13_Telegram.mp4"
        ],
        "Дисквалифицирован по жизни": [
            //"[DC] Isekai Shikkaku - 02_Telegram.mp4",
            //"[DC] Isekai Shikkaku - 03_Telegram.mp4",
            //"[DC] Isekai Shikkaku - 04_Telegram.mp4",
            //"[DC] Isekai Shikkaku - 05_Telegram.mp4",
            //"[DC] Isekai Shikkaku - 06_Telegram.mp4",
            //"[DC] Isekai Shikkaku - 07_Telegram.mp4",
            //"[DC] Isekai Shikkaku - 08_Telegram.mp4",
            //"[DC] Isekai Shikkaku - 09_Telegram.mp4",
            //"[DC] Isekai Shikkaku - 10_Telegram.mp4",
            //"[DC] Isekai Shikkaku - 11_Telegram.mp4"
        ],
        "Жизнь с моей сводной сестрой": [
            //"[DC] Gimai Seikatsu - 02_Telegram.mp4",
            //"[DC] Gimai Seikatsu - 03_Telegram.mp4",
            //"[DC] Gimai Seikatsu - 04_Telegram.mp4",
            //"[DC] Gimai Seikatsu - 05_Telegram.mp4",
            //"[DC] Gimai Seikatsu - 06_Telegram.mp4",
            //"[DC] Gimai Seikatsu - 07_Telegram.mp4",
            //"[DC] Gimai Seikatsu - 08_Telegram.mp4",
            //"[DC] Gimai Seikatsu - 09_Telegram.mp4",
            //"[DC] Gimai Seikatsu - 10_Telegram.mp4",
            //"[DC] Gimai Seikatsu - 11_Telegram.mp4",
            //"[DC] Gimai Seikatsu - 12_Telegram.mp4"
        ],
        "Маленький гражданин": [
            //"[DC] Shoushimin Series - 02_Telegram.mp4",
            //"[DC] Shoushimin Series - 04_Telegram.mp4",
            //"[DC] Shoushimin Series - 05_Telegram.mp4",
            //"[DC] Shoushimin Series - 06_Telegram.mp4",
            //"[DC] Shoushimin Series - 07_Telegram.mp4",
            //"[DC] Shoushimin Series - 08_Telegram.mp4",
            //"[DC] Shoushimin Series - 09_Telegram.mp4"
        ],
        "Пока-пока, Земля": [
            //"[DC] Bye Bye Earth - 02_Telegram.mp4",
            //"[DC] Bye Bye Earth - 04_Telegram.mp4",
            //"[DC] Bye Bye Earth - 05_Telegram.mp4",
            //"[DC] Bye Bye Earth - 06_Telegram.mp4",
            //"[DC] Bye Bye Earth - 07_Telegram.mp4",
            //"[DC] Bye Bye Earth - 09_Telegram.mp4",
            //"[DC] Bye Bye Earth - 10_Telegram.mp4"
        ],
        "Слишком много проигравших героинь!": [
            //"[DC] Make Heroine ga Oosugiru - 03_Telegram.mp4",
            //"[DC] Make Heroine ga Oosugiru - 04_Telegram.mp4",
            //"[DC] Make Heroine ga Oosugiru - 06_Telegram.mp4",
            //"[DC] Make Heroine ga Oosugiru - 08_Telegram.mp4",
            //"[DC] Make Heroine ga Oosugiru - 09_Telegram.mp4",
            //"[DC] Make Heroine ga Oosugiru - 10_Telegram.mp4",
            //"[DC] Make Heroine ga Oosugiru - 11_Telegram.mp4"
        ],
        "Я стал самым сильным с провальным навыком «ненормальное состояние», я разрушу всё": [
            //"[DC] Hazurewaku - 02_Telegram.mp4",
            //"[DC] Hazurewaku - 03_Telegram.mp4",
            //"[DC] Hazurewaku - 04_Telegram.mp4",
            //"[DC] Hazurewaku - 06_Telegram.mp4",
            //"[DC] Hazurewaku - 07_Telegram.mp4",
            //"[DC] Hazurewaku - 08_Telegram.mp4",
            //"[DC] Hazurewaku - 09_Telegram.mp4",
            //"[DC] Hazurewaku - 10_Telegram.mp4",
            //"[DC] Hazurewaku - 11_Telegram.mp4"
        ]
    };

    const files = async (dir, obj) => {
        for (const d of obj.k()) {
            const files = obj[d];
            //log(d);
            //log(files);
            for await (const f of files) {
                const path = resolve(dir, d, f)
                log(path);
                if (execute || !setMetrics) await ffmpeg(path);
            }
        }
    };

    if (n === 0 || n === 1) await files(dir, dirs);
    if (n === 0 || n === 2) await files(dirJ, dirs0);
    if (n === 0 || n === 3) await files(dirDC, dirs1);
};

export const testParams0 = async (i, i1, i2, out, n = 0) => {
    if (!setMetrics) {
        setParams(25);
        if (n === 0) await ffmpeg(i, out, '_HEVC-0');
        if (n === 1) await ffmpeg(i1, out, '_HEVC-cmd-05');
        if (n === 2) await ffmpeg(i2, out, '_HEVC-25');
    }
};

export const _mi = async (o, o1, o2) => {
    const info0 = await mi(o.replace('HEVC', 'HEVC-10'));
    log(info0);
    const info1 = await mi(o1.replace('-cmd', '-cmd(05)'));
    log(info1);
    const info2 = await mi(o2.replace('HEVC', 'HEVC-05'));
    log(info2);
};

export const mkv = async (out) => {
    await getVideos(out, async ({ name }) => {
        const o = resolve(`${out}/${name}`);
        log(o);
        await mkvAddTags(o);
    });
};

export const s = async (i) => {
    const _mi = await mi(i);
    const _s = await size(_mi);
    log(_mi);
    log(_s);
    return _s;
};

export const _setMetrics = async (i, o) => {
    await appendFiles(`${i}\n`, reportFile, logFile);
    await getMetrics(i, o);
    await appendFiles('\n', reportFile, logFile);
};

export const testMetrics = async (i, o) => {
    log(metrics);
    await _setMetrics(i, o);
};

export const opts = async (i, o, out, report = false, info = false) => {
    const n0 = 0;
    const n2 = 10;
    const n3 = 15;
    const n4 = 25;
    const l0 = 13;
    const l1 = 13;
    const l3 = 24;
    const l4 = 28; //38
    let n = n4;
    const l = n === n0 ? l0 : (n === n4 ? l4 : l3);

    const ffArr0 = ['ref', 'aq', 'psy', 'limit', 'rc', 'deblock', 'aq-s', 'aq-s=1', 'rc=60', '', 'no-intra', 'rdoq-lvl=2', 'rdoq:rc=60', 'no-sao']; // 0-13 | 25-38
    const ffArr1 = ffArr0.slice(n0, n2); // 0-9 | 15-19 + 20-24 | 25-28 + 29-33
    const ffArr2 = ffArr0.slice(n2, l1 + 1); // 10-13
    const ffArr = n === n0 || n === n4 ? ffArr0 : l === l0 ? ffArr1 : n === n2 ? ffArr2 : ffArr1;

    log(n);
    log(l);
    log(ffArr);

    if (setMetrics) log(metrics);

    for await (const p of ffArr) {
        const _p = `HEVC-${n > 0 && n < 10 ? `0${n}` : n}`;
        const _o = o.replace('HEVC', _p);
        if (n === l + 1) break;
        if (n === 9 || n === 14/* || n < 32*/) { n++; continue; }
        log(_o);
        setParams(n);
        if (execute) {
            if (setMetrics) await _setMetrics(_o, i);
            else await ffmpeg(i, out, `_${_p}`);
            if (report) {
                const { s: _s } = await mkvReport({ _o, s: (await s(i)).s });
                log(_s);
            }
            if (info) {
                const _info = await mi(_o);
                log(_info);
            }
        }
        n++;
    }
};

export const params = (params) => {
    log(params);
    setParams();
    log(params);
};

export const testSingle = async (o, i, i1, out) => {
    if (setMetrics) await _setMetrics(o, i);
    else await ffmpeg(i1, out, `_HEVC-39`);
};

export const report = async (get = false) => {
    const reports = await getFiles(ffreportDir, false);
    log(reports);

    if (!get) return;

    for await (const r of reports) {
        await appendFiles(`${r}\n`, FFreport, FFlog);
        log(await getReportInfo('ff', false, { report: r }));
        await appendFiles('\n', FFreport, FFlog);
    }
};

export const img = async (img1, img2) => {
    //const info1 = await mi(img1);
    //log(info1);
    //const info2 = await mi(img2);
    //log(info2);
    const tags1 = await exif(img1);
    log(tags1);
    const tags2 = await exif(img2);
    log(tags2);
};

export const parse = async () => await parseReports(reportFile, logFile);

export default testParams2;
