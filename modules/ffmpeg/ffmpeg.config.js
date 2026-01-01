import { log } from 'console';
import { resolve } from 'path';
import { args, mkdir, writeFile } from './baseHelpers.js';
import { getPresetType } from './presets.opts.js';

const {
    root = 'D:/Convert/FFMetrics',
    filesRoot = 'F:/Convert',
    i: input = null, o = true,
    includeBaseMetrics = false, psnr = true, ssim = true, vmaf = false, hide_banner = false, endall = false, scale = false, format = false, scaleOriginal = true,
    type: presetType = null
} = args;

export const
    [fps, ca, ba, ar, ac, v_ext, a_ext, rm_und_a, rm_tmp, aReport] = [23.976, 'aac', 192, 48000, 2, 'mkv', 'mka', true, false, true],
    mkvExt = ['mkv', 'mka'],
    lang_remove = ['ja', 'en'],
    codec_remove = ['AC-3'];

export const {
    test = false, withMetrics = false, parse = false, n = 51,
    mi: setInfo = true, metrics: setMetrics = false, exec: execute = false, execS = false, propedit = true, report: setReport = execute || execS,
    vstats = false, map: setMap = true, chapters: setChapters = true, metadata: setMetadata = false, cv = null, fmt = null, bit = null,
    sar_v = '1/1', sar: setSar = true, dar: setDar = true, vf_sar = false, vf_dar = false, m_sar = vf_sar, m_dar = vf_dar,
    /*
    passthrough (0) Каждый кадр с меткой времени передаётся из демультиплексора в мультиплексор.
    cfr (1) Кадры будут дублироваться и отбрасываться для достижения заданной постоянной частоты кадров.
    vfr (2) Кадры передаются с указанием временной метки или отбрасываются, чтобы у двух кадров не было одинаковой временной метки.
    auto (-1) Выбор между cfr и vfr зависит от возможностей мультиплексора. Это метод по умолчанию.
    */
    fpsMode = 'passthrough', // passthrough
    r: setR = false, c_range = true, c_space = c_range, c_chroma = c_range, vf_flags = 'bicubic', // bicubic|lanczos
    vf: setVf = true, vf_in = false, vf_range = true, scale_range = vf_range, scale_space = true, chroma_loc = vf_range, vf_space = true, vf_ispace = false, format_space = vf_space,
    crop: setCrop = false, border: setBorder = false, bsf: setBsf = false, out_scale_m = true, scale_m = true,
    hw = false, nv = false, crf = null, cq = null, qp = null,
    bv = null, preset = null, type = getPresetType(presetType) || getPresetType(preset), profile = null, level: setLvl = true, tier: setTier = false, tune = null,
    params = null, lthreads = 0, space: setSpace = true, p_space = setSpace, ca: setCa = null, ba: setBa = null, cs = null, s = true, y = true, postfix = '_HEVC', t = 0 // 30
} = args;

export const
    reportDir = `${root}/reports`,
    reportLogsDir = `${reportDir}/logs`,
    logDir = `${root}/logs`,
    ffreportDir = `${root}/ffreports`,
    reportFile = `${root}/FFMetrics_report.txt`,
    logFile = `${root}/FFMetrics_log.txt`,
    FFreportFile = resolve(`${root}/ffreport.txt`),
    FFreportCopy = resolve(`${root}/ffreports_copy.txt`),
    FFreportCopyLog = resolve(`${root}/ffreports_copy_log.txt`),
    FFreportErr = resolve(`${root}/ffreports_err.txt`),
    FFreport = FFreportFile.replace('report', '-report'),
    FFlog = FFreportFile.replace('report', 'report_log'),
    FFcmdLog = FFreportFile.replace('report', 'report-cmd');

export const
    i = input || `${filesRoot}/files`,
    out = o === false ? i : (o === true ? `${filesRoot}/out` : o),
    ffmpeg_c_dir = 'ffmpeg_c',
    ffmpeg_a_dir = 'ffmpeg_a',
    [a_dir, mkvMergeRoot, ffmpeg_a_root] = [`${out}/a`, `${out}/mkvMerge`, `${out}/${ffmpeg_a_dir}`];

const metricOpts = { psnr, ssim, vmaf };
const parseOpts = { hide_banner, r: setR, endall, scale, format, scaleOriginal };
const metricList = Object.entries(metricOpts).map(([k, v]) => v ? k : v).filter(m => m);

export const metrics = [];
metricList.forEach(m => { if (includeBaseMetrics) metrics.push(m); Object.entries(parseOpts).forEach(([k, v], i) => { if (v) metrics.push(`${m}${i + 1}`); }); });

await mkdir(reportLogsDir);
await mkdir(logDir);
await mkdir(ffreportDir);
await mkdir(out);

//log(args);
//log(parseOpts);
//log(metrics);

//await writeFile(reportFile, '');
//await writeFile(logFile, '');
//await writeFile(FFreport, '');
//await writeFile(FFlog, '');
