(function() {
  window.translate = null;
  angular.module('SensefyConfiguration', [
      'ngCookies',
      'ngResource',
      'ngSanitize',
      'ngRoute',
      'pascalprecht.translate'
  ]).constant('SensefyAPIUrl', 'http://localhost:8080/auth/').constant('SensefyTokenCreatePath', 'token').constant('SensefySemanticSearchKeywordBased', 'search/keywordSearch').constant('SensefyEntityDrivenSearch', 'search/entityDrivenSearch').constant('SensefyGetEntitiesByDoc', 'search/showEntitiesByDocId').constant('SensefyPreviewDoc', 'search/docs/preview').constant('SensefySmartAutocompletePhase1', 'search/autocomplete/1').constant('SensefySmartAutocompletePhase2', 'search/autocomplete/2').constant('SensefySmartAutocompletePhase3', 'search/autocomplete/3').constant('SensefyDocsPreview', 'search/docs/preview').constant('SensefySortOptions', [
    {
      'id': 1,
      'sortOption': 'Relevance',
      'sortId': 'score',
      'defaultSort': 'DESC'
    }, {
      'id': 2,
      'sortOption': 'Name',
      'sortId': 'name_sort',
      'defaultSort': 'ASC'
    }, {
      'id': 3,
      'sortOption': 'Title',
      'sortId': 'title_sort',
      'defaultSort': 'ASC'
    }, {
      'id': 4,
      'sortOption': 'Created',
      'sortId': 'ds_creation_date',
      'defaultSort': 'DESC'
    }, {
      'id': 5,
      'sortOption': 'Modified',
      'sortId': 'ds_last_modified',
      'defaultSort': 'DESC'
    }, {
      'id': 6,
      'sortOption': 'Creator',
      'sortId': 'ds_creator_sort',
      'defaultSort': 'ASC'
    }, {
      'id': 7,
      'sortOption': 'Modifier',
      'sortId': 'ds_last_modifier_sort',
      'defaultSort': 'ASC'
    }
  ]).constant('SensefyMlt', 'search/docs/mlt').constant('SensefyFacetsPerGroup', 2).constant('SensefyTranslations', {
    'en-us': {
      'language': 'Language',
      'english': 'English',
      'spanish': 'Spanish',
      'All': 'All',
      'txtUsername': 'Username',
      'txtPassword': 'Password',
      'txtCaptcha': 'Captcha',
      'msgCaptcha': 'System detected that number of failed login attempts!. Please fill the text from image to verify.',
      'login': 'Login',
      'logout': 'Logout',
      'errorBadCredentials': 'Bad credentials. Try again!',
      'errorBadUsernameOrPassword': 'Username or Password are incorrect. Please check them',
      'errorBadUsernameOrPasswordOrCaptcha': 'Username or Password or Captcha are incorrect. Please check them',
      'errorCaptch': 'Captcha is incorrect. Please check',
      'errorExpiredSession': 'Session expired. Please login again',
      'errorAPINotAvailable': 'Sensefy API is not available at this moment',
      'txtEnterTextToSearch': 'Please enter text to search',
      'txtEntities': 'Entities',
      'txtEntityTypes': 'EntityTypes',
      'txtDocumentTitles': 'Document titles',
      'txtDocumentSuggestions': 'Suggestions',
      'txtNoSuggestions': 'No suggestions',
      'txtDocumentsFound': 'Showing results {{docStart}}-{{docStart+docPerPage}} of {{documents}}.',//Showing results 1-10 of 1404.
      'txtDocumentsCountFound': 'documents found',
      'txtNoDocumentsFound': 'No documents found.',
      'txtDidYouMean': 'Did you mean:',
      'txtSortByTitle': 'Sort by Title',
      'txtSimilarityOfDocuments': 'Similarity of Documents',
      'txtNoSimilarDocumentsFound': 'No similar documents found',
      'txtSimilarDocuments': 'Similar documents',
      'txtSeedDocument': 'Seed document',
      'txtLow': 'Low',
      'txtHigh': 'High',
      'today': 'Today',
      'yesterday': 'Yesterday',
      'last_week': 'Last Week',
      'last_month': 'Last Month',
      'firstPage': 'First',
      'lastPage': 'Last',
      'nextPage': 'Next',
      'previousPage': 'Previous',
      'Creator': 'Creator',
      'Mimetype': 'Document Type',
      'Language': 'Language',
      'Size': 'Size',
      'Last Modified Date': 'Last Modified Date',
      'Creation Date': 'Creation Date',
      'sorting.hint': 'Sorting options',
      'Relevance': 'Relevance',
      'Name': 'Name',
      'Title': 'Title',
      'Created': 'Created',
      'Modified': 'Modified',
      'Creator': 'Creator',
      'Modifier': 'Modifier',
      'txtBackBtn': 'Back',
      'txtLanguages': 'Sensefy offered in ',
      'data.moreMetadata': 'More metadata',
      'data.size': 'Size',
      'data.datasource': 'Datasource',
      'data.modifier': 'Modifier',
      'data.modified': 'Modified',
      'data.language': 'Language',
      'data.author': 'Author',
      'data.created': 'Creation Date',
      'preview.page': 'Page',
      'preview.of': 'of',
      'preview.nopreview': 'No preview available',
      'preview.notsupported': 'Preview not supported yet',
      'preview.metadata': 'Metadata',
      'preview.name': 'Name',
      'preview.title': 'Title',
      'preview.description': 'Description',
      'preview.author': 'Author',
      'preview.datasource': 'Datasource',
      'preview.datasource_type': 'Datasource type',
      'preview.created': 'Creation Date',
      'preview.creator': 'Creator',
      'preview.modified': 'Last Modified',
      'preview.modifier': 'Last Modifier',
      'preview.language': 'Language',
      'preview.mimetype': 'Mimetype',
      'preview.size': 'Size',
      'preview.gotofolder': 'Go to folder container',
      'preview.close': 'Close',
      'preview.error': 'Error loading the preview',
      'mlt.hint': 'More like this',
      'application/json': 'JSON',
      'application/eps': 'EPS Type PostScript',
      'image/jp2': 'JPEG 2000 Image',
      'application/vnd.ms-project': 'Microsoft Project',
      'image/vnd.adobe.photoshop': 'Adobe Photoshop',
      'image/vnd.adobe.premiere': 'Adobe Premiere',
      'audio/vnd.adobe.soundbooth': 'Adobe SoundBooth',
      'application/vnd.adobe.aftereffects.project': 'Adobe AfterEffects Project',
      'application/vnd.adobe.aftereffects.template': 'Adobe AfterEffects Template',
      'application/framemaker': 'Adobe FrameMaker',
      'application/pagemaker': 'Adobe PageMaker',
      'application/remote-printing': 'Printer Text File',
      'text/plain': 'Plain Text',
      'text/csv': 'Comma Separated Values (CSV)',
      'text/html': 'HTML',
      'text/mediawiki': 'MediaWiki Markup',
      'application/xhtml+xml': 'XHTML',
      'application/postscript': 'PostScript',
      'application/illustrator': 'Adobe Illustrator File',
      'audio/x-aiff': 'AIFF Audio',
      'application/acp': 'Alfresco Content Package',
      'application/vnd.visio': 'Microsoft Visio',
      'application/vnd.adobe.xdp+xml': 'Adobe Acrobat XML Data Package',
      'audio/basic': 'Basic Audio',
      'video/ogg': 'Ogg Video',
      'audio/ogg': 'Ogg Audio',
      'audio/vorbis': 'Ogg Vorbis Audio',
      'application/ogg': 'Ogg Multiplex',
      'audio/x-flac': 'FLAC Audio',
      'video/webm': 'WebM Video',
      'video/x-msvideo': 'MS Video',
      'video/x-ms-asf': 'MS ASF Streaming Video',
      'video/x-ms-wmv': 'MS WMV Streaming Video',
      'audio/x-ms-wma': 'MS WMA Streaming Audio',
      'video/x-rad-screenplay': 'RAD Screen Display',
      'application/octet-stream': 'Binary File (Octet Stream)',
      'application/x-dosexec': 'Binary File',
      'image/cgm': 'CGM Image',
      'application/java': 'Java Class',
      'text/css': 'Style Sheet',
      'application/wordperfect': 'WordPerfect',
      'text/xml': 'XML',
      'image/gif': 'GIF Image',
      'application/x-gtar': 'GZIP Tarball',
      'application/x-gzip': 'GZIP',
      'text/calendar': 'iCalendar File',
      'image/ief': 'IEF Image',
      'image/bmp': 'Bitmap Image',
      'image/x-ms-bmp': 'MS Bitmap Image',
      'image/jpeg': 'JPEG Image',
      'image/svg+xml': 'Scalable Vector Graphics Image',
      'image/x-raw-adobe': 'Adobe Digital Negative Image',
      'image/x-raw-hasselblad': 'Hasselblad RAW Image',
      'image/x-raw-fuji': 'Fuji RAW Image',
      'image/x-raw-canon': 'Canon RAW Image',
      'image/x-raw-kodak': 'Kodak RAW Image',
      'image/x-raw-minolta': 'Minolta RAW Image',
      'image/x-raw-nikon': 'Nikon RAW Image',
      'image/x-raw-olympus': 'Olympus RAW Image',
      'image/x-raw-pentax': 'Pentax RAW Image',
      'image/x-raw-sony': 'Sony RAW Image',
      'image/x-raw-sigma': 'Sigma RAW Image',
      'image/x-raw-panasonic': 'Panasonic RAW Image',
      'image/x-raw-leica': 'Leica RAW Image',
      'image/x-raw-red': 'RED RAW Image',
      'application/x-javascript': 'JavaScript',
      'application/x-latex': 'LaTeX',
      'application/x-troff-man': 'Man Page',
      'video/mpeg': 'MPEG Video',
      'audio/mpeg': 'MPEG Audio',
      'audio/mp4': 'MPEG4 Audio',
      'video/mp4': 'MPEG4 Video',
      'video/x-m4v': 'MPEG4 Video (m4v)',
      'video/mpeg2': 'MPEG2 Video',
      'video/mp2t': 'MPEG Transport Stream',
      'video/quicktime': 'Quicktime Video',
      'video/3gpp': '3G Video',
      'video/3gpp2': '3G2 Video',
      'video/x-sgi-movie': 'SGI Video',
      'image/x-portable-bitmap': 'Portable Bitmap',
      'application/pdf': 'Adobe PDF Document',
      'image/x-portable-graymap': 'Greymap Image',
      'image/png': 'PNG Image',
      'image/x-portable-anymap': 'Anymap Image',
      'image/x-portable-pixmap': 'Pixmap Image',
      'image/x-cmu-raster': 'Raster Image',
      'image/x-rgb': 'RGB Image',
      'application/rtf': 'Rich Text Format',
      'text/richtext': 'Rich Text',
      'text/sgml': 'SGML (Human Readable)',
      'application/sgml': 'SGML (Machine Readable)',
      'application/x-sh': 'Shell Script',
      'application/vnd.adobe.air-application-installer-package+zip': 'Adobe AIR',
      'application/x-shockwave-flash': 'Shockwave Flash',
      'video/x-flv': 'Flash Video',
      'application/x-fla': 'Flash Source',
      'application/dita+xml': 'DITA',
      'application/x-tar': 'Tarball',
      'application/x-tex': 'Tex',
      'application/x-texinfo': 'Tex Info',
      'image/tiff': 'TIFF Image',
      'text/tab-separated-values': 'Tab Separated Values',
      'audio/x-wav': 'WAV Audio',
      'x-world/x-vrml': 'VRML',
      'image/x-xbitmap': 'XBitmap Image',
      'image/x-xpixmap': 'XPixmap Image',
      'image/x-xwindowdump': 'XWindow Dump',
      'application/x-compress': 'Z Compress',
      'application/zip': 'ZIP',
      'application/vnd.android.package-archive': 'Android Package',
      'image/vnd.dwg': 'AutoCAD Drawing',
      'image/x-dwt': 'AutoCAD Template',
      'message/rfc822': 'EMail',
      'application/vnd.ms-outlook': 'Microsoft Outlook Message',
      'application/msword': 'Microsoft Word',
      'application/vnd.ms-powerpoint': 'Microsoft PowerPoint',
      'application/vnd.ms-excel': 'Microsoft Excel',
      'application/vnd.openxmlformats-officedocument.wordprocessingml.document': 'Microsoft Word 2007',
      'application/vnd.ms-word.document.macroenabled.12': 'Microsoft Word 2007 macro-enabled document',
      'application/vnd.openxmlformats-officedocument.wordprocessingml.template': 'Microsoft Word 2007 template',
      'application/vnd.ms-word.template.macroenabled.12': 'Microsoft Word 2007 macro-enabled document template',
      'application/vnd.openxmlformats-officedocument.presentationml.presentation': 'Microsoft PowerPoint 2007',
      'application/vnd.ms-powerpoint.presentation.macroenabled.12': 'Microsoft PowerPoint 2007 macro-enabled presentation',
      'application/vnd.openxmlformats-officedocument.presentationml.slideshow': 'Microsoft PowerPoint 2007 slide show',
      'application/vnd.ms-powerpoint.slideshow.macroenabled.12': 'Microsoft PowerPoint 2007 macro-enabled slide show',
      'application/vnd.openxmlformats-officedocument.presentationml.template': 'Microsoft PowerPoint 2007 template',
      'application/vnd.ms-powerpoint.template.macroenabled.12': 'Microsoft PowerPoint 2007 macro-enabled presentation template',
      'application/vnd.ms-powerpoint.addin.macroenabled.12': 'Microsoft PowerPoint 2007 add-in',
      'application/vnd.openxmlformats-officedocument.presentationml.slide': 'Microsoft PowerPoint 2007 slide',
      'application/vnd.ms-powerpoint.slide.macroenabled.12': 'Microsoft PowerPoint 2007 macro-enabled slide',
      'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet': 'Microsoft Excel 2007',
      'application/vnd.openxmlformats-officedocument.spreadsheetml.template': 'Microsoft Excel template 2007',
      'application/vnd.ms-excel.sheet.macroenabled.12': 'Microsoft Excel 2007 macro-enabled workbook',
      'application/vnd.ms-excel.template.macroenabled.12': 'Microsoft Excel 2007 macro-enabled workbook template',
      'application/vnd.ms-excel.addin.macroenabled.12': 'Microsoft Excel 2007 add-in',
      'application/vnd.ms-excel.sheet.binary.macroenabled.12': 'Microsoft Excel 2007 binary workbook',
      'application/x-zip': 'Adobe Flex Project File',
      'application/x-indesign': 'Adobe InDesign Document',
      'application/vnd.apple.keynote': 'Apple iWork Keynote',
      'application/vnd.apple.pages': 'Apple iWork Pages',
      'application/vnd.apple.numbers': 'Apple iWork Numbers',
      'text/x-markdown': 'Markdown',
      'application/rss+xml': 'RSS',
      'text/x-java-source': 'Java Source File',
      'text/x-jsp': 'Java Server Page',
      'application/java-archive': 'Java Archive',
      'application/x-rar-compressed': 'RAR Archive',
      'application/vnd.oasis.opendocument.text': 'OpenDocument Text (OpenOffice 2.0)',
      'application/vnd.oasis.opendocument.text-template': 'OpenDocument Text Template',
      'application/vnd.oasis.opendocument.text-web': 'HTML Document Template',
      'application/vnd.oasis.opendocument.text-master': 'OpenDocument Master Document',
      'application/vnd.oasis.opendocument.graphics': 'OpenDocument Drawing',
      'application/vnd.oasis.opendocument.graphics-template': 'OpenDocument Drawing Template',
      'application/vnd.oasis.opendocument.presentation': 'OpenDocument Presentation',
      'application/vnd.oasis.opendocument.presentation-template': 'OpenDocument Presentation Template',
      'application/vnd.oasis.opendocument.spreadsheet': 'OpenDocument Spreadsheet',
      'application/vnd.oasis.opendocument.spreadsheet-template': 'OpenDocument Spreadsheet Template',
      'application/vnd.oasis.opendocument.chart': 'OpenDocument Chart',
      'application/vnd.oasis.opendocument.formula': 'OpenDocument Formula',
      'application/vnd.oasis.opendocument.database': 'OpenDocument Database',
      'application/vnd.oasis.opendocument.image': 'OpenDocument Image',
      'application/vnd.sun.xml.calc': 'OpenOffice 1.0/StarOffice6.0 Calc 6.0',
      'application/vnd.sun.xml.calc.template': 'OpenOffice 1.0/StarOffice6.0 Calc 6.0 Template',
      'application/vnd.sun.xml.draw': 'OpenOffice 1.0/StarOffice6.0 Draw 6.0',
      'application/vnd.sun.xml.impress': 'OpenOffice 1.0/StarOffice6.0 Impress 6.0',
      'application/vnd.sun.xml.impress.template': 'OpenOffice 1.0/StarOffice6.0 Impress 6.0 Template',
      'application/vnd.sun.xml.writer': 'OpenOffice 1.0/StarOffice6.0 Writer 6.0',
      'application/vnd.sun.xml.writer.template': 'OpenOffice 1.0/StarOffice6.0 Writer 6.0 Template',
      'application/vnd.stardivision.draw': 'StarDraw 5.x',
      'application/vnd.stardivision.calc': 'StarCalc 5.x',
      'application/vnd.stardivision.impress': 'StarImpress 5.x',
      'application/vnd.stardivision.impress-packed': 'StarImpress Packed 5.x',
      'application/vnd.stardivision.chart': 'StaChart 5.x',
      'application/vnd.stardivision.writer': 'StarWriter 5.x',
      'application/vnd.stardivision.writer-global': 'StarWriter 5.x global',
      'application/vnd.stardivision.math': 'StarMath 5.x',
      'aa': 'Afar',
      'ab': 'Abkhazian',
      'ae': 'Avestan',
      'af': 'Afrikaans',
      'ak': 'Akan',
      'am': 'Amharic',
      'an': 'Aragonese',
      'ar': 'Arabic',
      'as': 'Assamese',
      'av': 'Avaric',
      'ay': 'Aymara',
      'az': 'Azerbaijani',
      'ba': 'Bashkir',
      'be': 'Belarusian',
      'bg': 'Bulgarian',
      'bh': 'Bihari languages',
      'bi': 'Bislama',
      'bm': 'Bambara',
      'bn': 'Bengali',
      'bo': 'Tibetan',
      'br': 'Breton',
      'bs': 'Bosnian',
      'ca': 'Catalan',
      'ce': 'Chechen',
      'ch': 'Chamorro',
      'co': 'Corsican',
      'cr': 'Cree',
      'cs': 'Czech',
      'cu': 'Church Slavic',
      'cv': 'Chuvash',
      'cy': 'Welsh',
      'da': 'Danish',
      'de': 'German',
      'dv': 'Dhivehi',
      'dz': 'Dzongkha',
      'ee': 'Ewe',
      'el': 'Modern Greek',
      'en': 'English',
      'en-GB': 'British English',
      'en_US': 'English (US)',
      'eo': 'Esperanto',
      'es': 'Spanish',
      'es-ES': 'Spanish (Spain)',
      'et': 'Estonian',
      'eu': 'Basque',
      'fa': 'Persian',
      'ff': 'Fulah',
      'fi': 'Finnish',
      'fj': 'Fijian',
      'fo': 'Faroese',
      'fr': 'French',
      'fy': 'Western Frisian',
      'ga': 'Irish',
      'gd': 'Scottish Gaelic',
      'gl': 'Galician',
      'gn': 'Guarani',
      'gu': 'Gujarati',
      'gv': 'Manx',
      'ha': 'Hausa',
      'he': 'Hebrew',
      'hi': 'Hindi',
      'ho': 'Hiri Motu',
      'hr': 'Croatian',
      'ht': 'Haitian',
      'hu': 'Hungarian',
      'hy': 'Armenian',
      'hz': 'Herero',
      'ia': 'Interlingua',
      'id': 'Indonesian',
      'ie': 'Interlingue',
      'ig': 'Igbo',
      'ii': 'Sichuan Yi',
      'ik': 'Inupiaq',
      'io': 'Ido',
      'is': 'Icelandic',
      'it': 'Italian',
      'iu': 'Inuktitut',
      'ja': 'Japanese',
      'jv': 'Javanese',
      'ka': 'Georgian',
      'kg': 'Kongo',
      'ki': 'Kikuyu',
      'kj': 'Kuanyama',
      'kk': 'Kazakh',
      'kl': 'Kalaallisut',
      'km': 'Central Khmer',
      'kn': 'Kannada',
      'ko': 'Korean',
      'kr': 'Kanuri',
      'ks': 'Kashmiri',
      'ku': 'Kurdish',
      'kv': 'Komi',
      'kw': 'Cornish',
      'ky': 'Kirghiz',
      'la': 'Latin',
      'lb': 'Luxembourgish',
      'lg': 'Ganda',
      'li': 'Limburgan',
      'ln': 'Lingala',
      'lo': 'Lao',
      'lt': 'Lithuanian',
      'lu': 'Luba-Katanga',
      'lv': 'Latvian',
      'mg': 'Malagasy',
      'mh': 'Marshallese',
      'mi': 'Maori',
      'mk': 'Macedonian',
      'ml': 'Malayalam',
      'mn': 'Mongolian',
      'mr': 'Marathi',
      'ms': 'Malay',
      'mt': 'Maltese',
      'my': 'Burmese',
      'na': 'Nauru',
      'nb': 'Norwegian Bokmål',
      'nd': 'North Ndebele',
      'ne': 'Nepali',
      'ng': 'Ndonga',
      'nl': 'Dutch',
      'nn': 'Norwegian Nynorsk',
      'no': 'Norwegian',
      'nr': 'South Ndebele',
      'nv': 'Navajo',
      'ny': 'Nyanja',
      'oc': 'Occitan',
      'oj': 'Ojibwa',
      'om': 'Oromo',
      'or': 'Oriya',
      'os': 'Ossetian',
      'pa': 'Panjabi',
      'pi': 'Pali',
      'pl': 'Polish',
      'ps': 'Pushto',
      'pt': 'Portuguese',
      'qu': 'Quechua',
      'rm': 'Romansh',
      'rn': 'Rundi',
      'ro': 'Romanian',
      'ru': 'Russian',
      'rw': 'Kinyarwanda',
      'sa': 'Sanskrit',
      'sc': 'Sardinian',
      'sd': 'Sindhi',
      'se': 'Northern Sami',
      'sg': 'Sango',
      'sh': ' Serbo-Croatian',
      'si': 'Sinhala',
      'sk': 'Slovak',
      'sl': 'Slovenian',
      'sm': 'Samoan',
      'sn': 'Shona',
      'so': 'Somali',
      'sq': 'Albanian',
      'sr': 'Serbian',
      'ss': 'Swati',
      'st': 'Southern Sotho',
      'su': 'Sundanese',
      'sv': 'Swedish',
      'sw': 'Swahili',
      'ta': 'Tamil',
      'te': 'Telugu',
      'tg': 'Tajik',
      'th': 'Thai',
      'ti': 'Tigrinya',
      'tk': 'Turkmen',
      'tl': 'Tagalog',
      'tn': 'Tswana',
      'to': 'Tonga (Tonga Islands)',
      'tr': 'Turkish',
      'ts': 'Tsonga',
      'tt': 'Tatar',
      'tw': 'Twi',
      'ty': 'Tahitian',
      'ug': 'Uighur',
      'uk': 'Ukrainian',
      'ur': 'Urdu',
      'uz': 'Uzbek',
      've': 'Venda',
      'vi': 'Vietnamese',
      'vo': 'Volapük',
      'wa': 'Walloon',
      'wo': 'Wolof',
      'xh': 'Xhosa',
      'yi': 'Yiddish',
      'yo': 'Yoruba',
      'za': 'Zhuang',
      'zh': 'Chinese',
      'zu': 'Zulu',
      'Unknown': 'Unknown'
    },
    'es-es': {
      'language': 'Idioma',
      'english': 'Inglés',
      'spanish': 'Español',
      'All': 'Todos',
      'txtUsername': 'Nombre de usuario',
      'txtPassword': 'Contraseña',
      'txtCaptcha': 'Captcha',
      'msgCaptcha': 'System detected that number of failed login attempts!. Please fill the text from image to verify.',
      'login': 'Iniciar sesión',
      'logout': 'Cerrar sesión',
      'errorBadCredentials': 'Credenciales inválidas. Inténtelo de nuevo',
      'errorBadUsernameOrPassword': 'Nombre de usuario o contraseña incorrectos. Por favor compruébelos',
      'errorBadUsernameOrPasswordOrCaptcha': 'Username or Password or Captcha are incorrect. Please check them',
      'errorCaptch': 'Captcha is incorrect. Please check',
      'errorExpiredSession': 'Sesión expirada. Inicie sesión de nuevo',
      'errorAPINotAvailable': 'Sensefy API no disponible en este momento',
      'txtEnterTextToSearch': 'Por favor introduzca el texto a buscar',
      'txtEntities': 'Entidades',
      'txtEntityTypes': 'Tipos de entidades',
      'txtDocumentTitles': 'Títulos de documentos',
      'txtDocumentSuggestions': 'Sugerencias',
      'txtNoSuggestions': 'No hay sugerencias',
      'txtDocumentsFound': 'Showing results(need to translate) {{docStart}}-{{docStart+docPerPage}} of {{documents}}.',
      'txtDocumentsCountFound': ' documentos encontrados',
      'txtNoDocumentsFound': 'No se han encontrado documentos.',
      'txtDidYouMean': 'Quisiste decir:',
      'txtSortByTitle': 'Ordenar por titulo',
      'txtSimilarityOfDocuments': 'Similitud de los documentos',
      'txtNoSimilarDocumentsFound': 'No se han encontrado documentos similares',
      'txtSimilarDocuments': 'Documentos similares',
      'txtSeedDocument': 'Documento semilla',
      'txtLow': 'Bajo',
      'txtHigh': 'Alto',
      'today': 'Hoy',
      'yesterday': 'Ayer',
      'last_week': 'Semana pasada',
      'last_month': 'Mes pasado',
      'firstPage': 'Primera',
      'lastPage': 'Última',
      'nextPage': 'Siguiente',
      'previousPage': 'Anterior',
      'Creator': 'Creador',
      'Mimetype': 'Tipo de documento',
      'Language': 'Lenguaje',
      'Size': 'Tamaño',
      'Last Modified Date': 'Fecha de última modificación',
      'Creation Date': 'Fecha de creación',
      'sorting.hint': 'Ordenación',
      'Relevance': 'Relevancia',
      'Name': 'Nombre',
      'Title': 'Título',
      'Created': 'Creado',
      'Modified': 'Modificado',
      'Creator': 'Creador',
      'Modifier': 'Modificador',
      'txtBackBtn': 'Atrás',
      'txtLanguages': 'Sensefy disponible en ',
      'data.moreMetadata': 'Más metadatos',
      'data.size': 'Tamaño',
      'data.datasource': 'Origen de datos',
      'data.modifier': 'Modificador',
      'data.modified': 'Modificado',
      'data.language': 'Lenguaje',
      'data.author': 'Autor',
      'data.created': 'Fecha de creación',
      'preview.page': 'Página',
      'preview.of': 'de',
      'preview.nopreview': 'Vista previa no disponible',
      'preview.notsupported': 'Vista previa no soportada todavía',
      'preview.metadata': 'Metadatos',
      'preview.name': 'Nombre',
      'preview.title': 'Título',
      'preview.description': 'Descripción',
      'preview.author': 'Autor',
      'preview.datasource': 'Origen de datos',
      'preview.datasource_type': 'Tipo de origen de datos',
      'preview.created': 'Fecha de creación',
      'preview.creator': 'Creador',
      'preview.modified': 'Fecha última modificación',
      'preview.modifier': 'Modificador',
      'preview.language': 'Lenguaje',
      'preview.mimetype': 'Tipo MIME',
      'preview.size': 'Tamaño',
      'preview.gotofolder': 'Ir a carpeta contenedora',
      'preview.close': 'Cerrar',
      'preview.error': 'Error cargando la vista previa',
      'mlt.hint': 'Ver documentos similares',
      'application/json': 'JSON',
      'application/eps': 'EPS Type PostScript',
      'image/jp2': 'JPEG 2000 Image',
      'application/vnd.ms-project': 'Microsoft Project',
      'image/vnd.adobe.photoshop': 'Adobe Photoshop',
      'image/vnd.adobe.premiere': 'Adobe Premiere',
      'audio/vnd.adobe.soundbooth': 'Adobe SoundBooth',
      'application/vnd.adobe.aftereffects.project': 'Adobe AfterEffects Project',
      'application/vnd.adobe.aftereffects.template': 'Adobe AfterEffects Template',
      'application/framemaker': 'Adobe FrameMaker',
      'application/pagemaker': 'Adobe PageMaker',
      'application/remote-printing': 'Printer Text File',
      'text/plain': 'Plain Text',
      'text/csv': 'Comma Separated Values (CSV)',
      'text/html': 'HTML',
      'text/mediawiki': 'MediaWiki Markup',
      'application/xhtml+xml': 'XHTML',
      'application/postscript': 'PostScript',
      'application/illustrator': 'Adobe Illustrator File',
      'audio/x-aiff': 'AIFF Audio',
      'application/acp': 'Alfresco Content Package',
      'application/vnd.visio': 'Microsoft Visio',
      'application/vnd.adobe.xdp+xml': 'Adobe Acrobat XML Data Package',
      'audio/basic': 'Basic Audio',
      'video/ogg': 'Ogg Video',
      'audio/ogg': 'Ogg Audio',
      'audio/vorbis': 'Ogg Vorbis Audio',
      'application/ogg': 'Ogg Multiplex',
      'audio/x-flac': 'FLAC Audio',
      'video/webm': 'WebM Video',
      'video/x-msvideo': 'MS Video',
      'video/x-ms-asf': 'MS ASF Streaming Video',
      'video/x-ms-wmv': 'MS WMV Streaming Video',
      'audio/x-ms-wma': 'MS WMA Streaming Audio',
      'video/x-rad-screenplay': 'RAD Screen Display',
      'application/octet-stream': 'Binary File (Octet Stream)',
      'application/x-dosexec': 'Binary File',
      'image/cgm': 'CGM Image',
      'application/java': 'Java Class',
      'text/css': 'Style Sheet',
      'application/wordperfect': 'WordPerfect',
      'text/xml': 'XML',
      'image/gif': 'GIF Image',
      'application/x-gtar': 'GZIP Tarball',
      'application/x-gzip': 'GZIP',
      'text/calendar': 'iCalendar File',
      'image/ief': 'IEF Image',
      'image/bmp': 'Bitmap Image',
      'image/x-ms-bmp': 'MS Bitmap Image',
      'image/jpeg': 'JPEG Image',
      'image/svg+xml': 'Scalable Vector Graphics Image',
      'image/x-raw-adobe': 'Adobe Digital Negative Image',
      'image/x-raw-hasselblad': 'Hasselblad RAW Image',
      'image/x-raw-fuji': 'Fuji RAW Image',
      'image/x-raw-canon': 'Canon RAW Image',
      'image/x-raw-kodak': 'Kodak RAW Image',
      'image/x-raw-minolta': 'Minolta RAW Image',
      'image/x-raw-nikon': 'Nikon RAW Image',
      'image/x-raw-olympus': 'Olympus RAW Image',
      'image/x-raw-pentax': 'Pentax RAW Image',
      'image/x-raw-sony': 'Sony RAW Image',
      'image/x-raw-sigma': 'Sigma RAW Image',
      'image/x-raw-panasonic': 'Panasonic RAW Image',
      'image/x-raw-leica': 'Leica RAW Image',
      'image/x-raw-red': 'RED RAW Image',
      'application/x-javascript': 'JavaScript',
      'application/x-latex': 'LaTeX',
      'application/x-troff-man': 'Man Page',
      'video/mpeg': 'MPEG Video',
      'audio/mpeg': 'MPEG Audio',
      'audio/mp4': 'MPEG4 Audio',
      'video/mp4': 'MPEG4 Video',
      'video/x-m4v': 'MPEG4 Video (m4v)',
      'video/mpeg2': 'MPEG2 Video',
      'video/mp2t': 'MPEG Transport Stream',
      'video/quicktime': 'Quicktime Video',
      'video/3gpp': '3G Video',
      'video/3gpp2': '3G2 Video',
      'video/x-sgi-movie': 'SGI Video',
      'image/x-portable-bitmap': 'Portable Bitmap',
      'application/pdf': 'Adobe PDF Document',
      'image/x-portable-graymap': 'Greymap Image',
      'image/png': 'PNG Image',
      'image/x-portable-anymap': 'Anymap Image',
      'image/x-portable-pixmap': 'Pixmap Image',
      'image/x-cmu-raster': 'Raster Image',
      'image/x-rgb': 'RGB Image',
      'application/rtf': 'Rich Text Format',
      'text/richtext': 'Rich Text',
      'text/sgml': 'SGML (Human Readable)',
      'application/sgml': 'SGML (Machine Readable)',
      'application/x-sh': 'Shell Script',
      'application/vnd.adobe.air-application-installer-package+zip': 'Adobe AIR',
      'application/x-shockwave-flash': 'Shockwave Flash',
      'video/x-flv': 'Flash Video',
      'application/x-fla': 'Flash Source',
      'application/dita+xml': 'DITA',
      'application/x-tar': 'Tarball',
      'application/x-tex': 'Tex',
      'application/x-texinfo': 'Tex Info',
      'image/tiff': 'TIFF Image',
      'text/tab-separated-values': 'Tab Separated Values',
      'audio/x-wav': 'WAV Audio',
      'x-world/x-vrml': 'VRML',
      'image/x-xbitmap': 'XBitmap Image',
      'image/x-xpixmap': 'XPixmap Image',
      'image/x-xwindowdump': 'XWindow Dump',
      'application/x-compress': 'Z Compress',
      'application/zip': 'ZIP',
      'application/vnd.android.package-archive': 'Android Package',
      'image/vnd.dwg': 'AutoCAD Drawing',
      'image/x-dwt': 'AutoCAD Template',
      'message/rfc822': 'EMail',
      'application/vnd.ms-outlook': 'Microsoft Outlook Message',
      'application/msword': 'Microsoft Word',
      'application/vnd.ms-powerpoint': 'Microsoft PowerPoint',
      'application/vnd.ms-excel': 'Microsoft Excel',
      'application/vnd.openxmlformats-officedocument.wordprocessingml.document': 'Microsoft Word 2007',
      'application/vnd.ms-word.document.macroenabled.12': 'Microsoft Word 2007 macro-enabled document',
      'application/vnd.openxmlformats-officedocument.wordprocessingml.template': 'Microsoft Word 2007 template',
      'application/vnd.ms-word.template.macroenabled.12': 'Microsoft Word 2007 macro-enabled document template',
      'application/vnd.openxmlformats-officedocument.presentationml.presentation': 'Microsoft PowerPoint 2007',
      'application/vnd.ms-powerpoint.presentation.macroenabled.12': 'Microsoft PowerPoint 2007 macro-enabled presentation',
      'application/vnd.openxmlformats-officedocument.presentationml.slideshow': 'Microsoft PowerPoint 2007 slide show',
      'application/vnd.ms-powerpoint.slideshow.macroenabled.12': 'Microsoft PowerPoint 2007 macro-enabled slide show',
      'application/vnd.openxmlformats-officedocument.presentationml.template': 'Microsoft PowerPoint 2007 template',
      'application/vnd.ms-powerpoint.template.macroenabled.12': 'Microsoft PowerPoint 2007 macro-enabled presentation template',
      'application/vnd.ms-powerpoint.addin.macroenabled.12': 'Microsoft PowerPoint 2007 add-in',
      'application/vnd.openxmlformats-officedocument.presentationml.slide': 'Microsoft PowerPoint 2007 slide',
      'application/vnd.ms-powerpoint.slide.macroenabled.12': 'Microsoft PowerPoint 2007 macro-enabled slide',
      'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet': 'Microsoft Excel 2007',
      'application/vnd.openxmlformats-officedocument.spreadsheetml.template': 'Microsoft Excel template 2007',
      'application/vnd.ms-excel.sheet.macroenabled.12': 'Microsoft Excel 2007 macro-enabled workbook',
      'application/vnd.ms-excel.template.macroenabled.12': 'Microsoft Excel 2007 macro-enabled workbook template',
      'application/vnd.ms-excel.addin.macroenabled.12': 'Microsoft Excel 2007 add-in',
      'application/vnd.ms-excel.sheet.binary.macroenabled.12': 'Microsoft Excel 2007 binary workbook',
      'application/x-zip': 'Adobe Flex Project File',
      'application/x-indesign': 'Adobe InDesign Document',
      'application/vnd.apple.keynote': 'Apple iWork Keynote',
      'application/vnd.apple.pages': 'Apple iWork Pages',
      'application/vnd.apple.numbers': 'Apple iWork Numbers',
      'text/x-markdown': 'Markdown',
      'application/rss+xml': 'RSS',
      'text/x-java-source': 'Java Source File',
      'text/x-jsp': 'Java Server Page',
      'application/java-archive': 'Java Archive',
      'application/x-rar-compressed': 'RAR Archive',
      'application/vnd.oasis.opendocument.text': 'OpenDocument Text (OpenOffice 2.0)',
      'application/vnd.oasis.opendocument.text-template': 'OpenDocument Text Template',
      'application/vnd.oasis.opendocument.text-web': 'HTML Document Template',
      'application/vnd.oasis.opendocument.text-master': 'OpenDocument Master Document',
      'application/vnd.oasis.opendocument.graphics': 'OpenDocument Drawing',
      'application/vnd.oasis.opendocument.graphics-template': 'OpenDocument Drawing Template',
      'application/vnd.oasis.opendocument.presentation': 'OpenDocument Presentation',
      'application/vnd.oasis.opendocument.presentation-template': 'OpenDocument Presentation Template',
      'application/vnd.oasis.opendocument.spreadsheet': 'OpenDocument Spreadsheet',
      'application/vnd.oasis.opendocument.spreadsheet-template': 'OpenDocument Spreadsheet Template',
      'application/vnd.oasis.opendocument.chart': 'OpenDocument Chart',
      'application/vnd.oasis.opendocument.formula': 'OpenDocument Formula',
      'application/vnd.oasis.opendocument.database': 'OpenDocument Database',
      'application/vnd.oasis.opendocument.image': 'OpenDocument Image',
      'application/vnd.sun.xml.calc': 'OpenOffice 1.0/StarOffice6.0 Calc 6.0',
      'application/vnd.sun.xml.calc.template': 'OpenOffice 1.0/StarOffice6.0 Calc 6.0 Template',
      'application/vnd.sun.xml.draw': 'OpenOffice 1.0/StarOffice6.0 Draw 6.0',
      'application/vnd.sun.xml.impress': 'OpenOffice 1.0/StarOffice6.0 Impress 6.0',
      'application/vnd.sun.xml.impress.template': 'OpenOffice 1.0/StarOffice6.0 Impress 6.0 Template',
      'application/vnd.sun.xml.writer': 'OpenOffice 1.0/StarOffice6.0 Writer 6.0',
      'application/vnd.sun.xml.writer.template': 'OpenOffice 1.0/StarOffice6.0 Writer 6.0 Template',
      'application/vnd.stardivision.draw': 'StarDraw 5.x',
      'application/vnd.stardivision.calc': 'StarCalc 5.x',
      'application/vnd.stardivision.impress': 'StarImpress 5.x',
      'application/vnd.stardivision.impress-packed': 'StarImpress Packed 5.x',
      'application/vnd.stardivision.chart': 'StaChart 5.x',
      'application/vnd.stardivision.writer': 'StarWriter 5.x',
      'application/vnd.stardivision.writer-global': 'StarWriter 5.x global',
      'application/vnd.stardivision.math': 'StarMath 5.x',
      'aa': 'Afar',
      'ab': 'Abkhaz',
      'ae': 'Avestan',
      'af': 'Africanos',
      'ak': 'Akan',
      'am': 'Amharic',
      'an': 'Aragonés',
      'ar': 'Árabe',
      'as': 'Assamese',
      'av': 'Avaric',
      'ay': 'Aymara',
      'az': 'Azerbaijani',
      'ba': 'Bashkir',
      'be': 'Belarús',
      'bg': 'Búlgaro',
      'bh': 'Bihari',
      'bi': 'Bislama',
      'bm': 'Bambara',
      'bn': 'Bengalí',
      'bo': 'Tibetano',
      'br': 'Breton',
      'bs': 'Bosnian',
      'ca': 'Catalán',
      'ce': 'Chechenio',
      'ch': 'Chamorro',
      'co': 'Corso',
      'cr': 'Cree',
      'cs': 'Checo',
      'cu': 'Antiguo eslavo ',
      'cv': 'Chuvashia',
      'cy': 'Galés',
      'da': 'Danés',
      'de': 'Alemán',
      'dv': 'Dhivehi',
      'dz': 'Dzongkha',
      'ee': 'Ewe',
      'el': 'Modern Greek',
      'en': 'Inglés',
      'en-GB': 'Inglés Británico',
      'en_US': 'Inglés (EEUU)',
      'eo': 'Esperanto',
      'es': 'Español',
      'es-ES': 'Español (España)',
      'et': 'Estonio',
      'eu': 'Vasco',
      'fa': 'Persa',
      'ff': 'Fulah',
      'fi': 'Finlandés',
      'fj': 'Fiji',
      'fo': 'Faroese',
      'fr': 'Francés',
      'fy': 'Oeste de Frisia',
      'ga': 'Irlandés',
      'gd': 'Scottish Gaelic',
      'gl': 'Gallego',
      'gn': 'Guarani',
      'gu': 'Gujarati',
      'gv': 'Manx',
      'ha': 'Hausa',
      'he': 'Hebrew',
      'hi': 'Hindi',
      'ho': 'Hiri Motu',
      'hr': 'Croato',
      'ht': 'Haitian',
      'hu': 'Hungarian',
      'hy': 'Armenio',
      'hz': 'Herero',
      'ia': 'Interlingua',
      'id': 'Indonesio',
      'ie': 'Interlingue',
      'ig': 'Igbo',
      'ii': 'Sichuan Yi',
      'ik': 'Inupiaq',
      'io': 'Ido',
      'is': 'Islandés',
      'it': 'Italiano',
      'iu': 'Inuktitut',
      'ja': 'Japonés',
      'jv': 'Javanese',
      'ka': 'Georgiano',
      'kg': 'Kongo',
      'ki': 'Kikuyu',
      'kj': 'Kuanyama',
      'kk': 'Kazakh',
      'kl': 'Kalaallisut',
      'km': 'Central Khmer',
      'kn': 'Kannada',
      'ko': 'Coreano',
      'kr': 'Kanuri',
      'ks': 'Kashmiri',
      'ku': 'Kurdish',
      'kv': 'Komi',
      'kw': 'Cornish',
      'ky': 'Kirghiz',
      'la': 'Latin',
      'lb': 'Luxembourgish',
      'lg': 'Ganda',
      'li': 'Limburgan',
      'ln': 'Lingala',
      'lo': 'Lao',
      'lt': 'Lithuanian',
      'lu': 'Luba-Katanga',
      'lv': 'Letón',
      'mg': 'Malagasy',
      'mh': 'Marshallese',
      'mi': 'Maori',
      'mk': 'Macedonio',
      'ml': 'Malayalam',
      'mn': 'Mongolés',
      'mr': 'Marathi',
      'ms': 'Malay',
      'mt': 'Maltés',
      'my': 'Burmese',
      'na': 'Nauru',
      'nb': 'Norwegian Bokmål',
      'nd': 'North Ndebele',
      'ne': 'Nepali',
      'ng': 'Ndonga',
      'nl': 'Holandés',
      'nn': 'Noruego Nynorsk',
      'no': 'Noruego',
      'nr': 'South Ndebele',
      'nv': 'Navajo',
      'ny': 'Nyanja',
      'oc': 'Occitan',
      'oj': 'Ojibwa',
      'om': 'Oromo',
      'or': 'Oriya',
      'os': 'Ossetian',
      'pa': 'Panjabi',
      'pi': 'Pali',
      'pl': 'Polaco',
      'ps': 'Pushto',
      'pt': 'Portugués',
      'qu': 'Quechua',
      'rm': 'Romanche',
      'rn': 'Rundi',
      'ro': 'Rumano',
      'ru': 'Ruso',
      'rw': 'Kinyarwanda',
      'sa': 'Sanskrit',
      'sc': 'Sardinian',
      'sd': 'Sindhi',
      'se': 'Sami del norte',
      'sg': 'Sango',
      'sh': ' Serbo-Croatian',
      'si': 'Sinhala',
      'sk': 'Eslovaco',
      'sl': 'Esloveno',
      'sm': 'Samoan',
      'sn': 'Shona',
      'so': 'Somalí',
      'sq': 'Albanian',
      'sr': 'Serbian',
      'ss': 'Swati',
      'st': 'Southern Sotho',
      'su': 'Sundanese',
      'sv': 'Sueco',
      'sw': 'Swahili',
      'ta': 'Tamil',
      'te': 'Telugu',
      'tg': 'Tajik',
      'th': 'Tailandés',
      'ti': 'Tigrinya',
      'tk': 'Turkmen',
      'tl': 'Tagalo',
      'tn': 'Tswana',
      'to': 'Tonga (Islas Tonga)',
      'tr': 'Turkish',
      'ts': 'Tsonga',
      'tt': 'Tatar',
      'tw': 'Twi',
      'ty': 'Tahitian',
      'ug': 'Uighur',
      'uk': 'Ucraniano',
      'ur': 'Urdu',
      'uz': 'Uzbek',
      've': 'Venda',
      'vi': 'Vietnamita',
      'vo': 'Volapük',
      'wa': 'Walloon',
      'wo': 'Wolof',
      'xh': 'Xhosa',
      'yi': 'Yiddish',
      'yo': 'Yoruba',
      'za': 'Zhuang',
      'zh': 'Chino',
      'zu': 'Zulu',
      'Unknown': 'Desconocido'
    }
  }).config([
    '$routeProvider', '$httpProvider', '$translateProvider', 'SensefyTranslations', '$sceProvider', function($routeProvider, $httpProvider, $translateProvider, SensefyTranslations, $sceProvider) {
      var data, lang;
      $sceProvider.enabled(false);
      for (lang in SensefyTranslations) {
        data = SensefyTranslations[lang];
        $translateProvider.translations(lang, data);
      }
      $translateProvider.preferredLanguage('en-us');
      $routeProvider.when('/', {
        templateUrl: 'views/login.html',
        controller: 'LoginController'
      }).when('/search', {
        templateUrl: 'views/search.html',
        controller: 'SearchController',
        reloadOnSearch: false,
        resolve: {
          dataSources: function($q, $rootScope, SemanticSearchService, $location) {
            var deferrer;
            deferrer = $q.defer();
            if ($rootScope.user !== void 0) {
              SemanticSearchService.search('*:*', $rootScope.user.token, 0, 0, "*", [], true).then(function(response) {
                return deferrer.resolve(response);
              }, function(response) {
                deferrer.reject;
                return $location.path("/");
              });
            }
            return deferrer.promise;
          }
        }
      }).when('/mlt/:docId', {
        templateUrl: 'views/mlt.html',
        controller: 'MltController',
        resolve: {
          document: function($window, $rootScope, $route, SemanticSearchService, $location, $q) {
            var deferrer, docId, regExp;
            regExp = new RegExp("\\[SL\\]", "g");
            docId = $route.current.params.docId.replace(regExp, '/');
            if (docId.substring(0, 4) === "Sent") {
              docId = $window.encodeURIComponent(docId);
              docId = docId.replace('%2F', '/', 'g');
            }
            deferrer = $q.defer();
            if ($rootScope.user !== void 0) {
              SemanticSearchService.search('id:"' + docId + '"', $rootScope.user.token, 0, 1).then(function(response) {
                return deferrer.resolve(response);
              }, function(response) {
                deferrer.reject;
                return $location.path('/');
              });
              return deferrer.promise;
            }
          }
        }
      }).when('/preview/:docId', {
        templateUrl: 'views/preview.html',
        controller: 'PreviewController'
      }).when('/imgSearch', {
        templateUrl: 'views/imgSearch.html',
        controller: 'ImageSearchController'
      }).otherwise;
      ({
        redirectTo: '/'
      });
      $httpProvider.defaults.useXDomain = true;
      return delete $httpProvider.defaults.headers.common['X-Requested-With'];
    }
  ]).run([
    '$rootScope', '$location', '$window', '$http', '$translate', function($rootScope, $location, $window, $http, $translate) {
      return $rootScope.$on('$routeChangeStart', function(event, next, current) {
        $rootScope.loading = true;
        $rootScope.searchParameters = $rootScope.searchParameters || $location.search();
        if (next.originalPath !== '/login' && $rootScope.user === void 0) {
          $location.path('/');
          $location.search($location.search());
        }
        return window.translate = $translate;
      });
    }
  ]);
}.call(this));
(function() {
  var controllersModule;

  controllersModule = angular.module('SensefyControllers', ['pascalprecht.translate', 'tmh.dynamicLocale', 'LocalStorageModule']).config([
    'tmhDynamicLocaleProvider', function(tmhDynamicLocaleProvider) {
      return tmhDynamicLocaleProvider.localeLocationPattern('//code.angularjs.org/1.3.15/i18n/angular-locale_{{locale}}.js');
    }
  ]).config([
    'localStorageServiceProvider', function(localStorageServiceProvider) {
      return localStorageServiceProvider.setPrefix('sensefy');
    }
  ]).controller('RootController', [
    '$injector', '$scope', '$location', '$window', '$rootScope', '$translate', 'tmhDynamicLocale', 'localStorageService', function($injector, $scope, $location, $window, $rootScope, $translate, tmhDynamicLocale, localStorageService) {
      $scope.errors = {};
      tmhDynamicLocale.set('en-us');
      $scope.$on('userLogged', function(event, data) {
        $rootScope.user = {
          username: data.user,
          token: data.token
        };
        controllersModule.value("token", data.token);
        $location.path("/search");
        return $location.search($location.search());
      });
      $scope.currentLanguage = 'English';
      $scope.changeLanguage = function(lang) {
        if (lang == null) {
          lang = 'en-us';
        }
          switch(lang) {
              case 'en-us':
                  $scope.currentLanguage = 'English';
                  break;
              case 'es-es':
                  $scope.currentLanguage = 'Español';
                  break;
              default:
                  $scope.currentLanguage = 'English';
          }
        tmhDynamicLocale.set(lang);
        return $translate.use(lang);
      };
      if (localStorageService.get('token') !== null && localStorageService.get('user') !== null) {
        return $scope.$emit('userLogged', {
          user: localStorageService.get('user'),
          token: localStorageService.get('token')
        });
      }
    }
  ]).controller('LoginController', ['$log',
    '$scope', 'LoginService', 'localStorageService', function($log, $scope, LoginService, localStorageService) {
      $scope.user = {};
      $scope.loginError = false;
      $scope.doingLogin = false;
      $scope.timezone = jstz.determine().name();
      $scope.isCaptcha = false;
      $scope.captchaImgSrc = '';
      angular.element('#username').focus();
      return $scope.doLogin = function() {
        if (!$scope.user.username || $scope.user.username === null || $scope.user.username === '' || !$scope.user.password || $scope.user.password === null || $scope.user.password === '') {
          $scope.errors['login'] = true;
          $scope.errors['loginMessage'] = 'errorBadUsernameOrPassword';
          if ($scope.user.captcha === void 0 && $scope.isCaptcha) {
            $scope.errors['loginCaptcha'] = true;
            return $scope.errors['loginMessageCaptcha'] = 'errorBadUsernameOrPasswordOrCaptcha';
          } else {
            return $scope.errors['loginCaptcha'] = false;
          }
        } else {
          $scope.doingLogin = true;
          if ($scope.user.captcha === void 0 && $scope.isCaptcha) {
            $scope.errors['loginCaptcha'] = true;
            $scope.errors['loginMessageCaptcha'] = 'errorBadUsernameOrPasswordOrCaptcha';
            $scope.errors['login'] = false;
            return false;
          } else {
            $scope.errors['loginCaptcha'] = false;
          }
          
          return LoginService.login($scope.user.username, $scope.user.password, {
            timezone: $scope.timezone,
            captcha: $scope.user.captcha
          }).then(function(response) {
            $scope.doingLogin = false;
            if (response.data.captchaImage === null && response.data.error === null && response.data.token.value !== '') {
              $scope.isCaptcha = false;
              $scope.captchaImgSrc = '';
              $scope.errors['login'] = false;
              localStorageService.set('token', response.data.token.value);
              localStorageService.set('user', $scope.user.username);
              return $scope.$emit('userLogged', {
                user: localStorageService.get('user'),
                token: localStorageService.get('token')
              });
            } else if (response.data.captchaImage === null && response.data.error.msg !== null && response.data.token === null) {
              $scope.errors['login'] = true;
              $scope.errors['loginMessage'] = response.data.error.msg;
              $scope.isCaptcha = false;
              $scope.errors['loginCatcha'] = false;
              return $scope.captchaImgSrc = '';
            } else if (response.data.captchaImage !== null && response.data.error.msg !== null && response.data.token === null) {
              $scope.errors['login'] = true;
              $scope.errors['loginMessage'] = response.data.error.msg;
              $scope.errors['loginCatcha'] = false;
              $scope.isCaptcha = true;
              return $scope.captchaImgSrc = response.data.captchaImage;
            }
          }, function(response) {
            $scope.doingLogin = false;
            $scope.errors['login'] = true;
            if (response.status !== 200) {
              return $scope.errors['loginMessage'] = 'errorAPINotAvailable';
            }
          });
        }
      };
    }
  ]).controller('SearchController', [
      '$scope',
      '$location',
      'dataSources',
      '$rootScope',
      'SemanticSearchService',
      'SensefyFacetsPerGroup',
      'SensefySortOptions',
      'PDFViewerService',
      'SensefyAPIUrl',
      'SensefyDocsPreview',
      'localStorageService',
      'ApiService',
      'SensefyPreviewDoc',
      function($scope, $location, dataSources, $rootScope, SemanticSearchService, SensefyFacetsPerGroup, SensefySortOptions, pdf, SensefyAPIUrl, SensefyDocsPreview, localStorageService, ApiService, SensefyPreviewDoc) {
      var FILTER_LABEL_SEPARATOR, addMissingFacets, allSource, cleanLocationSearchParameters, entityMap, escapeHtmlExceptB, fillLocationSearchParameters, getActiveSource, getDataSourceByValue, initDataSources, parseFacets, parseSimpleFacet, processHighlightInfo, removeCluster, removeFilter, resetSelectedValues;
      FILTER_LABEL_SEPARATOR = '$$';
      angular.element('.ui.dropdown.top-right-menu').dropdown();
      angular.element('.ui.dropdown').dropdown({
          onChange: function(value, text, $selectedItem) {
              var temp = new Array();
              temp = value.split(",");
              $scope.sorting = temp[1].trim();
              $scope.setSorting(temp[0], temp[1])

              if ($(this).hasClass('sorting')){
                  if(value !== 'relevance'){
                      $('.asc-dec').show();
                  }
                  else{
                      $('.asc-dec').hide();
                  }
              }
          }
      });

      $scope.logout = function(error) {
        if (error == null) {
          error = true;
        }
        angular.element('body').addClass('ggl');
        $scope.user = void 0;
        localStorageService.clearAll();
        if (error) {
          $scope.errors['login'] = true;
          $scope.errors['loginMessage'] = 'errorExpiredSession';
        }
        $location.path('/');
      };

      allSource = {
        value: 'All',
        occurrence: 0,
        filter: '',
        active: true
      };
      $scope.allOccurrence = 0;
      initDataSources = function(response) {
        var facet, sources, _i, _len, _ref, allOccurrence, _lenK;
        sources = [];
        _ref = response.facets;
        $scope.sources = [];
        $scope.allOccurrence = 0;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          facet = _ref[_i];
          if (facet.label === 'Data Source') {
            sources = facet.facetItems;
            for (_k = 0, _lenK = sources.length; _k < _lenK; _k++) {
                $scope.allOccurrence += eval(sources[_k].occurrence);
            }
          }
        }
        allSource.occurrence = $scope.allOccurrence;
        sources.unshift(allSource);
        return $scope.sources = sources;
      };
      getActiveSource = function(defaultSource) {
        var active, s, _i, _len, _ref;
        if (defaultSource == null) {
          defaultSource = allSource;
        }
        _ref = $scope.sources;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          s = _ref[_i];
          if (s.active === true) {
            active = s;
          }
        }
        if (active) {
          return active;
        } else {
          return defaultSource;
        }
      };
      getDataSourceByValue = function(sources, value) {
        var source, _i, _len;
        for (_i = 0, _len = sources.length; _i < _len; _i++) {
          source = sources[_i];
          if (source.value === value) {
            return source;
          }
        }
      };
      $scope.clusters = [];
      $scope.originalCluster = [];
      parseFacets = function(response) {
                var activeSource, facet, _i, _len, _ref;
        activeSource = getActiveSource();
        $scope.facets = [];
        if ($scope.clusterSelected === false) {
          $scope.clusters = response.clusters;
          $scope.originalCluster = angular.copy(response.clusters);
        }
        _ref = response.facets;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          facet = _ref[_i];
          if (facet.label !== 'Data Source') {
            $scope.facets.push(parseSimpleFacet(facet));
          }
        }
        return addMissingFacets();
      };
      parseSimpleFacet = function (facet) {
          var fItem, fItems, fItemsFilters, filtered, humanReadableSize, intervalValues, itemValue, iv, lastSelected, res, x, _i, _j, _k, _len, _len1, _len2, _ref;
          humanReadableSize = function (size) {
              var i, sizes;
              if (size) {
                  sizes = [
                      'B',
                      'KB',
                      'MB',
                      'GB',
                      'TB'
                  ];
                  i = 0;
                  if (typeof size === 'string') {
                      size = parseInt(size);
                  }
                  while (++i && size > 1000) {
                      size = size / 1000;
                  }
                  return size.toFixed(2) + ' ' + sizes[i - 1];
              }
          };
          fItems = [];
          _ref = facet.facetItems;
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
              x = _ref[_i];
              if (facet.label === 'Size') {
                  itemValue = x.value;
                  intervalValues = itemValue.slice(1, -1).split(' TO ');
                  res = [];
                  for (_j = 0, _len1 = intervalValues.length; _j < _len1; _j++) {
                      iv = intervalValues[_j];
                      res.push(humanReadableSize(iv));
                  }
                  x.value = res.join(' - ');
              }
              if ($scope.isFacetSelect[x.filter]) {
                  fItems.unshift(x);
              } else {
                  fItems.push(x);
              }
          }
          filtered = $scope.filtersToShow.filter(function (e) {
            return e.facetLabel === facet.label;
          });
          fItemsFilters = fItems.map(function (e) {
            return e.filter;
          });
          for (_k = 0, _len2 = filtered.length; _k < _len2; _k++) {
            lastSelected = filtered[_k];
            if (fItemsFilters.indexOf(lastSelected.value) === -1) {
                fItem = {
                    value: lastSelected.label,
                    occurrence: 0,
                    filter: lastSelected.value
                };
                fItems.unshift(fItem);
            }
          }
          facet.facetItems = fItems;
          return facet;
      };
      addMissingFacets = function () {
                var f, facetLabel, filter, found, missingFacet, missingFacets, _i, _j, _len, _len1, _ref, _ref1, _results;
                missingFacets = {};
                _ref = $scope.filtersToShow;
                for (_i = 0, _len = _ref.length; _i < _len; _i++) {
                    filter = _ref[_i];
                    _ref1 = $scope.facets;
                    for (_j = 0, _len1 = _ref1.length; _j < _len1; _j++) {
                        f = _ref1[_j];
                        if (f.label === filter.facetLabel) {
                            found = f;
                        }
                    }
                    if (!!(found == null)) {
                        missingFacet = missingFacets[filter.facetLabel];
                        if (missingFacet === null || missingFacet === void 0) {
                            missingFacets[filter.facetLabel] = {
                                label: filter.facetLabel,
                                facetItems: []
                            };
                        }
                        missingFacets[filter.facetLabel].facetItems.push({
                            value: filter.label,
                            ocurrence: 0,
                            filter: filter.value
                        });
                    }
                }
                _results = [];
                for (facetLabel in missingFacets) {
                    missingFacet = missingFacets[facetLabel];
                    _results.push($scope.facets.push(missingFacet));
                }
                return _results;
            };
      $scope.searchOptions = {
        rows: 10,
        fields: 'id,name,title,mimetype,size,description,author,container_url,ds_creator,ds_creation_date,ds_last_modifier,ds_last_modified,url,preview_url,path,thumbnail_base64,data_source,data_source_type,language,image_length,image_width,Sharpness,focal_length,geo_lat,geo_long',
        start: 0
      };
      $scope.documents = [];
      $scope.queryTerm = '';
      $scope.sources = [];
      $scope.facets = [];
      $scope.clusters = [];
      $scope.filters = [];
      $scope.clusterFilters = [];
      $scope.clusterFiltersToShow = [];
      $scope.cluterQuery = '';
      $scope.clusterSelected = false;
      $scope.clusterSelectedSize = '';
      $scope.selectedCluterObj = [];
      $scope.filtersToShow = [];
      $scope.tmpFiltersToShow = [];
      $scope.showSuggestions = false;
      $scope.selectedEntity = null;
      $scope.selectedEntityType = null;
      $scope.selectedEntityTypeAttribute = null;
      $scope.selectedEntityTypeAttributeValue = null;
      $scope.selectedTitle = null;
      $scope.totalDocuments = 0;
      $scope.documentsOffsetStart = 0;
      $scope.documentsPerPage = 10;
      $scope.currentPage = 1;
      $scope.collatedQuery = null;
      $scope.sorting = null;
      $scope.titleSorting = "";
      $scope.maxSize = 5;
      $scope.searching = false;
      $scope.sortings = SensefySortOptions;
      $scope.selectSortings = $scope.sortings[0];
      $scope.isFacetSelect = [];


      angular.element('.ui.dropdown.pe-itemsPerPage').dropdown({
          onChange: function(value, text, $selectedItem) {
              $scope.documentsPerPage = value;
              $scope.updateDocumentOffset(false);
              $scope.runCurrentQuery();
          }
      });
      $scope.infiniteScroll = function() {
        if ($scope.totalDocuments > 0 && ($scope.currentPage * $scope.documentsPerPage < $scope.totalDocuments) && ($scope.searching === false)) {
          $scope.updateDocumentOffset(false);
          $scope.currentPage++;
          return $scope.runCurrentQuery();
        }
      };
      $scope.setCurrentPage = function (pageToStart) {
          window.scrollTo(0, 0);
          $scope.updateDocumentOffset(false);
          $scope.currentPage = pageToStart;
          $scope.runCurrentQuery();
      };
      $scope.cleanFilters = function() {
        var activeSource;
        $scope.filters = [];
        activeSource = getActiveSource();
        if (activeSource.filter !== '') {
          $scope.filters.push(activeSource.filter);
        }
        angular.forEach($scope.filtersToShow, function(filter, index) {
          return $scope.isFacetSelect[filter.value] = false;
        });
        return $scope.filtersToShow = [];
      };
      $scope.setActiveSource = function(source, runQuery) {
        var activeSource, pos, s, _i, _len, _ref;
        if (runQuery == null) {
          runQuery = true;
        }
        activeSource = getActiveSource();
        if (source.value !== activeSource.value) {
          pos = $scope.filters.indexOf(activeSource.filter);
          if (pos >= 0) {
            $scope.filters.splice(pos, 1) >= 0;
          }
          _ref = $scope.sources;
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            s = _ref[_i];
            s.active = false;
          }
          source.active = true;
          if (source.filter !== '') {
            $scope.filters.push(source.filter);
          }
          if (runQuery) {
            fillLocationSearchParameters();
          }
          if (runQuery) {
            return $scope.runCurrentQuery(false);
          }
        }
      };
      $scope.setSorting = function(sortId, sortType) {
        if(sortId===null || sortId===''){
            sortId = 'score';
        }
        if(sortType===null){
            sortType = 'DESC';
        }
        $scope.titleSorting = sortId + ' ' + sortType;
        $scope.sorting = sortType.trim();
        $scope.updateDocumentOffset(true);
        return $scope.runCurrentQuery(false);
      };
      $scope.addCluster = function(clusterItem, runQuery) {
        var cpos;
        if (runQuery == null) {
          runQuery = true;
        }
        $scope.selectedCluterObj = clusterItem;
        cpos = $scope.clusterFilters.indexOf(clusterItem.label);
        if (cpos >= 0) {
          $scope.cluterQuery = '';
          $scope.clusterSelected = false;
          removeCluster(clusterItem);
          return false;
        } else {
          $scope.cluterQuery = clusterItem.filterQuery;
          $scope.clusters = [clusterItem];
          $scope.clusterSelected = true;
          $scope.clusterSelectedSize = clusterItem.size;
        }
        $scope.clusterFilters.push(clusterItem.label);
        $scope.updateDocumentOffset(runQuery);
        if (runQuery) {
          fillLocationSearchParameters();
        }
        if (runQuery) {
          return $scope.runCurrentQuery(false, false);
        }
      };
      removeCluster = function(clusterItem) {
        var cposr;
        cposr = $scope.clusterFilters.indexOf(clusterItem.label);
        if (cposr >= 0) {
          $scope.clusterFilters.splice(cposr, 1);
        }
        fillLocationSearchParameters();
        $scope.updateDocumentOffset(true);
        return $scope.runCurrentQuery(false, true);
      };
      $scope.addFilter = function (facetItem, facetLabel, runQuery) {
        var filter, pos;
        if (runQuery == null) {
          runQuery = true;
        }
        pos = $scope.filters.indexOf(facetItem.filter);
        if (pos >= 0) {
          removeFilter(facetItem);
          return false;
        } else {
          filter = {
            facetLabel: facetLabel,
            label: facetItem.value,
            value: facetItem.filter,
              uriValue: facetItem.filter + FILTER_LABEL_SEPARATOR + facetItem.value + FILTER_LABEL_SEPARATOR + facetLabel
          };
          $scope.filters.push(filter.value);
          $scope.filtersToShow.push(filter);
        }
        $scope.updateDocumentOffset(runQuery);
        if (runQuery) {
          fillLocationSearchParameters();
        }
        if (runQuery) {
          $scope.runCurrentQuery(false);
        }
        return $scope.isFacetSelect[facetItem.filter] = true;
      };
      removeFilter = function(filter) {
        var index, posf;
        posf = $scope.filters.indexOf(filter.filter);
        if (posf >= 0) {
          $scope.filters.splice(posf, 1);
        }
        index = $scope.filtersToShow.map(function(e) {
          return e.value;
        }).indexOf(filter.filter);
        if (index >= 0) {
          $scope.filtersToShow.splice(index, 1);
        }
        fillLocationSearchParameters();
        $scope.updateDocumentOffset(true);
        $scope.runCurrentQuery(false);
        return $scope.isFacetSelect[filter.filter] = false;
      };
      $scope.updateDocumentOffset = function(restoreCurrentPage) {
        if (restoreCurrentPage == null) {
          restoreCurrentPage = true;
        }
        if (restoreCurrentPage) {
          $scope.currentPage = 1;
        }
        return $scope.documentsOffsetStart = ($scope.currentPage - 1) * $scope.documentsPerPage;
      };
      $scope.titleSelected = function(title, removeFilters) {
        var clustering;
        if (removeFilters == null) {
          removeFilters = true;
        }
        $scope.normalSearch = false;
        $scope.searching = true;
        $scope.queryTerm = title.document_suggestion;
        $scope.selectedTitle = title;
        if (removeFilters) {
          $scope.cleanFilters();
        }
        $scope.facets = {};
        $scope.updateDocumentOffset(removeFilters);
        return SemanticSearchService.search('id:"' + $scope.selectedTitle.id + '"', $scope.user.token, $scope.documentsOffsetStart, $scope.documentsPerPage, "*", $scope.filters, true, $scope.titleSorting, clustering = true).then(function(response) {
          $scope.documents = response.data.searchResults.documents || [];
          processHighlightInfo($scope.documents, response.data.searchResults.highlight);
          $scope.selectedEntity = response.data.searchResults.entity || $scope.selectedEntity;
          $scope.totalDocuments = response.data.searchResults.numFound;
          parseFacets(response.data);
          initDataSources(response.data);
          return $scope.searching = false;
        }, function(response) {
          return $scope.logout();
        });
      };
      $scope.suggestionSelected = function(suggestion, removeFilters) {
        var clustering;
        if (removeFilters == null) {
          removeFilters = true;
        }
        $scope.normalSearch = false;
        $scope.searching = true;
        $scope.queryTerm = suggestion;
        $scope.selectedTitle = null;
        if (removeFilters) {
          $scope.cleanFilters();
        }
        $scope.facets = {};
        $scope.updateDocumentOffset(removeFilters);
        return SemanticSearchService.search('"' + $scope.queryTerm + '"', $scope.user.token, $scope.documentsOffsetStart, $scope.documentsPerPage, "*", $scope.filters, true, $scope.titleSorting, clustering = true).then(function(response) {
          $scope.documents = response.data.searchResults.documents || [];
          processHighlightInfo($scope.documents, response.data.searchResults.highlight);
          $scope.selectedEntity = response.data.searchResults.entity || $scope.selectedEntity;
          $scope.totalDocuments = response.data.searchResults.numFound;
          parseFacets(response.data);
          initDataSources(response.data);
          return $scope.searching = false;
        }, function(response) {
          return $scope.logout();
        });
      };
      $scope.entitySelected = function(entity, removeFilters) {
        var clustering;
        if (removeFilters == null) {
          removeFilters = true;
        }
        $scope.normalSearch = false;
        $scope.selectedEntity = entity;
        if (removeFilters) {
          $scope.cleanFilters();
        }
        $scope.facets = {};
        $scope.updateDocumentOffset(removeFilters);
        return SemanticSearchService.searchByEntity($scope.selectedEntity.id, $scope.user.token, $scope.documentsOffsetStart, $scope.documentsPerPage, "*", $scope.filters, true, $scope.titleSorting, clustering = true).then(function(response) {
          $scope.documents = response.data.searchResults.documents || [];
          $scope.selectedEntity = response.data.searchResults.entity || $scope.selectedEntity;
          $scope.totalDocuments = response.data.searchResults.numFound;
          initDataSources(response.data);
          return parseFacets(response.data);
        }, function(response) {
          return $scope.logout();
        });
      };
      $scope.entityTypeSelected = function(entityType, removeFilters) {
        var clustering;
        if (removeFilters == null) {
          removeFilters = true;
        }
        $scope.normalSearch = false;
        $scope.selectedEntityType = entityType;
        if (removeFilters) {
          $scope.cleanFilters();
        }
        $scope.selectedEntity = null;
        $scope.queryTerm = '';
        $scope.updateDocumentOffset(removeFilters);
        return SemanticSearchService.searchByEntityType($scope.selectedEntityType.id, $scope.user.token, $scope.documentsOffsetStart, $scope.documentsPerPage, "*", $scope.filters, true, $scope.titleSorting, clustering = true).then(function(response) {
          $scope.documents = response.data.searchResults.documents || [];
          $scope.selectedEntityType = response.data.searchResults.entityType || $scope.selectedEntityType;
          $scope.totalDocuments = response.data.searchResults.numFound;
          initDataSources(response.data);
          return parseFacets(response.data);
        }, function(response) {
          return $scope.logout();
        });
      };
      $scope.entityTypeAttributeSelected = function(entityTypeAttribute, removeFilters) {
        if (removeFilters == null) {
          removeFilters = true;
        }
        $scope.normalSearch = false;
        $scope.selectedEntityTypeAttribute = entityTypeAttribute;
        if (removeFilters) {
          return $scope.cleanFilters();
        }
      };
      $scope.entityTypeAttributeValueSelected = function(value, removeFilters) {
        var clustering;
        if (removeFilters == null) {
          removeFilters = true;
        }
        $scope.normalSearch = false;
        $scope.selectedEntityTypeAttributeValue = value;
        $scope.selectedEntity = null;
        $scope.updateDocumentOffset(removeFilters);
        return SemanticSearchService.searchByEntityQuery($scope.selectedEntityType.id, $scope.selectedEntityTypeAttribute, $scope.selectedEntityTypeAttributeValue, $scope.user.token, $scope.documentsOffsetStart, $scope.documentsPerPage, "*", $scope.filters, true, $scope.titleSorting, clustering = true).then(function(response) {
          $scope.documents = response.data.searchResults.documents || [];
          $scope.selectedEntityType = response.data.searchResults.entityType || $scope.selectedEntityType;
          $scope.totalDocuments = response.data.searchResults.numFound;
            initDataSources(response.data);
          return parseFacets(response.data);
        }, function(response) {
          return $scope.logout();
        });
      };
      $scope.query = function(restorePagination, cluster) {
        var apiQuery, clustering;
        if (restorePagination == null) {
          restorePagination = true;
        }
        if (cluster == null) {
          cluster = false;
        }
        $scope.searching = true;
        if ($scope.queryTerm.length > 0) {
          $scope.normalSearch = true;
          $scope.collatedQuery = null;
          resetSelectedValues();
          if (restorePagination) {
            $scope.cleanFilters();
          }
          cleanLocationSearchParameters();
          fillLocationSearchParameters();
          $scope.updateDocumentOffset(restorePagination);
          if (restorePagination) {
            $scope.documents = [];
          }
          if ($scope.cluterQuery !== '') {
            apiQuery = $scope.cluterQuery;
          } else {
            apiQuery = $scope.queryTerm;
          }
          if ($scope.user && $scope.user.token) {
            return SemanticSearchService.search(apiQuery, $scope.user.token, $scope.documentsOffsetStart, $scope.documentsPerPage, $scope.searchOptions.fields, $scope.filters, true, $scope.titleSorting, clustering = cluster).then(function(response) {
              if (restorePagination || $scope.currentPage === 1) {
                $scope.documents = response.data.searchResults.documents || [];
              } else {
                $scope.documents = $scope.documents.concat(response.data.searchResults.documents);//response.data.searchResults.documents;
              }
              processHighlightInfo($scope.documents, response.data.searchResults.highlight);
              $scope.totalDocuments = response.data.searchResults.numFound;
              if (response.data.searchResults.collationQuery) {
                $scope.collatedQuery = response.data.searchResults.collationQuery;
              }
                initDataSources(response.data);
              parseFacets(response.data);
              $scope.searching = false;
              if ($scope.clusterSelectedSize !== '') {
                if ($scope.clusterSelectedSize > $scope.totalDocuments) {
                  $scope.selectedCluterObj.size = $scope.totalDocuments;
                  return $scope.clusters = [$scope.selectedCluterObj];
                }
              }
            }, function(response) {
              $scope.searching = false;
              return $scope.logout();
            });
          }
        } else {
          return $scope.searching = false;
        }
      };
      entityMap = {
          '<': '&lt;',
          '>': '&gt;'
      };
      escapeHtmlExceptB = function (string) {
          string = string.replace(/[<>]/g, function (s) {
              return entityMap[s];
          });
          string = string.replace('&lt;b&gt;', '<b>').replace('&lt;/b&gt;', '</b>');
          return string;
      };
      processHighlightInfo = function(documents, highlightInfo) {
        var doc, _i, _j, _k, _len, _len1, _len2, _results;
        if (highlightInfo == null) {
          highlightInfo = [];
        }
        if (highlightInfo === null || highlightInfo.length === 0) {
          return;
        }
        for (_i = 0, _len = documents.length; _i < _len; _i++) {
          doc = documents[_i];
          if (highlightInfo[doc.id] != null) {
            doc.hls = highlightInfo[doc.id];
          }
        }
          for (_j = 0, _len1 = documents.length; _j < _len1; _j++) {
              doc = documents[_j];
              doc.hls.content[0] = escapeHtmlExceptB(doc.hls.content[0]);
          }
          _results = [];
          for (_k = 0, _len2 = documents.length; _k < _len2; _k++) {
              doc = documents[_k];
              if (doc.content) {
                  _results.push(doc.content[0] = escapeHtmlExceptB(doc.content[0]));
              }
          }
          return _results;
      };
      $scope.loadEntities = function(document) {
        var filters;
        filters = [];
        if ($scope.selectedEntityType) {
          filters.push('type:"' + $scope.selectedEntityType.id + '"');
        }
        if ($scope.selectedEntityTypeAttribute && $scope.selectedEntityTypeAttributeValue) {
          filters.push($scope.selectedEntityTypeAttribute + ':"' + $scope.selectedEntityTypeAttributeValue + '"');
        }
        return SemanticSearchService.getEntities(document.id, $scope.user.token, 0, 10, "id,label,thumbnail", filters.join(" AND ")).then(function(response) {
          return document.entities = response.data.searchResults.documents || [];
        }, function(response) {
          document.entities = [];
          return $scope.logout();
        })["finally"](function() {
          return document.entitiesLoaded = true;
        });
      };
      $scope.runCurrentQuery = function(restorePagination, cluster) {
        if (restorePagination == null) {
          restorePagination = false;
        }
        if (cluster == null) {
          cluster = true;
        }
        $scope.searching = true;
        if ($scope.selectedEntityType && $scope.selectedEntityTypeAttribute && $scope.selectedEntityTypeAttributeValue) {
          return $scope.entityTypeAttributeValueSelected($scope.selectedEntityTypeAttributeValue, false);
        }
        if ($scope.selectedEntityType !== null && $scope.selectedEntityTypeAttribute === null && $scope.selectedEntityTypeAttributeValue === null) {
          return $scope.entityTypeSelected($scope.selectedEntityType, false);
        }
        if ($scope.selectedEntity !== null) {
          return $scope.entitySelected($scope.selectedEntity, false);
        }
        if ($scope.selectedTitle !== null) {
          return $scope.titleSelected($scope.selectedTitle, false);
        }
        return $scope.query(restorePagination, cluster);
      };
      $scope.$watch('selectSortings', function(newValue, oldValue) {
        return $scope.setSorting($scope.sortings[0].sortId, $scope.sortings[0].defaultSort);
      });
      $scope.$on('entitySelected', function(event, entity) {
        resetSelectedValues();
        $scope.entitySelected(entity);
        cleanLocationSearchParameters();
        return fillLocationSearchParameters();
      });
      $scope.$on('entityTypeSelected', function(event, entityType) {
        resetSelectedValues();
        $scope.entityTypeSelected(entityType);
        cleanLocationSearchParameters();
        return fillLocationSearchParameters();
      });
      $scope.$on('entityTypeAttributeSelected', function(event, entityTypeAttribute) {
        $scope.entityTypeAttributeSelected(entityTypeAttribute);
        cleanLocationSearchParameters();
        return fillLocationSearchParameters();
      });
      $scope.$on('entityTypeAttributeValueSelected', function(event, attribute, value) {
        $scope.entityTypeAttributeValueSelected(value);
        cleanLocationSearchParameters();
        return fillLocationSearchParameters();
      });
      $scope.$on('titleSelected', function(event, title) {
        resetSelectedValues();
        $scope.titleSelected(title);
        cleanLocationSearchParameters();
        return fillLocationSearchParameters();
      });
      $scope.$on('suggestionSelected', function(event, suggestion) {
        resetSelectedValues();
        $scope.queryTerm = suggestion;
        cleanLocationSearchParameters();
        return fillLocationSearchParameters();
      });
      $scope.$on('entityTypeCleaned', function() {
        $scope.selectedEntityType = null;
        $scope.selectedEntityTypeAttribute = null;
        $scope.selectedEntityTypeAttributeValue = null;
        return $scope.queryTerm = "";
      });
      $scope.$on('entityTypeAttributeCleaned', function() {
        $scope.selectedEntityTypeAttribute = null;
        $scope.selectedEntityTypeAttributeValue = null;
        return $scope.queryTerm = "";
      });
      $scope.$on('entityTypeAttributeCleaned', function() {
        $scope.selectedEntityTypeAttributeValue = null;
        return $scope.queryTerm = "";
      });
      resetSelectedValues = function() {
        $scope.selectedEntity = null;
        $scope.selectedEntityType = null;
        $scope.selectedEntityTypeAttribute = null;
        $scope.selectedEntityTypeAttributeValue = null;
        return $scope.selectedTitle = null;
      };
      cleanLocationSearchParameters = function() {
        $location.search('query', null);
        $location.search('entityId', null);
        $location.search('entityType', null);
        $location.search('entityTypeAttribute', null);
        $location.search('entityTypeAttributeValue', null);
        $location.search('title', null);
        $location.search('source', null);
        return $location.search('filters', null);
      };
      fillLocationSearchParameters = function() {
        var filters;
        if ($scope.queryTerm) {
          $location.search('query', $scope.queryTerm);
        }
        if ($scope.selectedEntity) {
          $location.search('entityId', $scope.selectedEntity.id);
        }
        if ($scope.selectedEntityType) {
          $location.search('entityType', $scope.selectedEntityType.id);
        }
        if ($scope.selectedEntityTypeAttribute) {
          $location.search('entityTypeAttribute', $scope.selectedEntityTypeAttribute);
        }
        if ($scope.selectedEntityTypeAttributeValue) {
          $location.search('entityTypeAttributeValue', $scope.selectedEntityTypeAttributeValue);
        }
        if ($scope.selectedTitle) {
          $location.search('title', $scope.selectedTitle.id);
        }
        if ($scope.selectedTitle) {
          $location.search('query', $scope.selectedTitle.document_suggestion);
        }
        $location.search('source', $scope.activeSource);
        filters = [];
        angular.forEach($scope.filtersToShow, function(filter, index) {
          return filters.push(filter.uriValue);
        });
        $location.search('filters', null);
        if (filters.length > 0) {
          return $location.search('filters', filters.join(','));
        }
      };
          $scope.cleanSearchParameters = function () {
              resetSelectedValues();
              cleanLocationSearchParameters();
              angular.element('header').addClass('ggl');
              $scope.queryTerm = '';
              $scope.documents = [];
              $scope.facets = [];
              $scope.clusters = [];
              $scope.filters = [];
              $scope.clusterFilters = [];
              $scope.clusterFiltersToShow = [];
              $scope.selectedCluterObj = [];
              $scope.filtersToShow = [];
              $scope.tmpFiltersToShow = [];
              $scope.showSuggestions = false;
              $scope.totalDocuments = -1;
              $scope.currentPage = 1;
              $scope.collatedQuery = null;
              $scope.sorting = null;
              $scope.titleSorting = '';
              $scope.isFacetSelect = [];
              return $location.path('/search');
          };
      $scope.initialize = function() {
        var entity, params, parsedFilters;
        params = $location.search();
        if (params.query !== void 0) {
          $scope.queryTerm = params.query;
        }
        if (params.entityId) {
          entity = {
            id: params.entityId
          };
          $scope.selectedEntity = entity;
        }
        if (params.entityType) {
          $scope.selectedEntityType = {
            id: params.entityType
          };
          if (params.entityTypeAttribute !== void 0 && params.entityTypeAttributeValue !== void 0) {
            $scope.selectedEntityTypeAttribute = params.entityTypeAttribute;
            $scope.selectedEntityTypeAttributeValue = params.entityTypeAttributeValue;
          }
        }
        if (params.title) {
          $scope.selectedTitle = {
            id: params.title
          };
          if ($scope.queryTerm !== void 0) {
            $scope.selectedTitle.document_suggestion = $scope.queryTerm;
          }
        }
        if (params.source) {
          $scope.setActiveSource(params.source, false);
        }
        if (params.filters) {
          parsedFilters = params.filters.split(',');
          angular.forEach(parsedFilters, function(filter, key) {
            var facetEntity, parts, value;
            if (filter !== '') {
              parts = filter.split(FILTER_LABEL_SEPARATOR);
              if (parts.length === 3) {
                value = parts[1];
                facetEntity = {
                  value: value,
                  filter: parts[0]
                };
                return $scope.addFilter(facetEntity, parts[2], false);
              }
            }
          });
        }
        return $scope.runCurrentQuery();
      };
      $scope.initialize();
      $scope.$on('$locationChangeSuccess', function(event) {
        $scope.cleanFilters();
        return $scope.initialize();
      });
      $scope.logoClick = function() {
        $scope.cleanFilters();
        window.history.go(-1);
        return $scope.queryTerm = '';
      };
      $scope.SensefyFacetsPerGroupMin = SensefyFacetsPerGroup;
      $scope.humanReadableSize = function(size) {
        var i, sizes;
        if (size) {
          sizes = ['B', 'KB', 'MB', 'GB', 'TB'];
          i = 0;
          while (++i && size > 1024) {
            size = size / 1024;
          }
          return size.toFixed(2) + ' ' + sizes[i - 1];
        }
      };
      $scope.dateTimeFormatter = function(date){
          var dateUI = '';
          var pubDate = new Date(date);
          var toDay = new Date();
          var timeDiff = Math.abs(toDay.getTime() - pubDate.getTime());
          var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24));
          var diffHours = Math.ceil(timeDiff / (1000 * 3600));
          var diffMinutes = Math.ceil(timeDiff / (1000*36));
          var monthNames = [
              "January", "February", "March",
              "April", "May", "June", "July",
              "August", "September", "October",
              "November", "December"
          ];

          var newTime = '', timeSign = '';
          var dateTime = date.split("T");

          var date = dateTime[0].split("-");
          var time = dateTime[1].split(":");

          if(parseInt(time[0])>12){
              newTime = parseInt(time[0]) - 12;
          }
          else{
              newTime = parseInt(time[0]);
          }

          if(time[0]>12){
              timeSign = 'PM';
          }
          else{
              timeSign = 'AM';
          }

          if(diffDays>8){
              dateUI = 'on '+monthNames[parseInt(date[1])-1]+' ' + date[2]+', '+ date[0]+' '+newTime+':'+ time[1]+' '+timeSign;
          }

          if((diffDays<=7) && (diffDays>=2)){
              dateUI = diffDays+' days ago.';
          }

          if((diffDays<=1) && (diffHours<=24) && (diffHours>1)){
              dateUI = diffHours+' hours ago.';
          }

          if((diffDays===1) && (diffHours===1) && (diffMinutes<60)){
              dateUI = diffMinutes+' mins ago.';
          }

          return dateUI;
      };
      $scope.urlTruncate = function (fullStr, strLen, separator) {
          if (fullStr.length <= strLen) return fullStr;

          strLen = strLen || 50;
          separator = separator || '...';

          var sepLen = separator.length,
              charsToShow = strLen - sepLen,
              frontChars = Math.ceil(charsToShow/2),
              backChars = Math.floor(charsToShow/2);

          return fullStr.substr(0, frontChars) + separator + fullStr.substr(fullStr.length - backChars);
      };

      $scope.pickDocIcon = function(docType){
          var docIcon;
          switch (docType) {
              case 'application/msword':
                  docIcon = "word";
                  break;
              case 'application/pdf':
                  docIcon = "pdf";
                  break;
              case 'application/vnd.ms-excel':
                  docIcon = "excel";
                  break;
              case 'application/zip':
                  docIcon = "archive";
                  break;
              case 'application/x-tex':
                  docIcon = "text";
                  break;
              case 'image/tiff':
                  docIcon = "image";
                  break;
          }

          return docIcon;
      };
      $scope.viewer = pdf.Instance("viewer");
      $scope.isClose = false;
      $scope.previewNextPage = function() {
        return $scope.viewer.nextPage();
      };
      $scope.previewPreviousPage = function() {
        return $scope.viewer.prevPage();
      };
      $scope.pageLoaded = function(curPage, totalPages) {
        $scope.previewCurrentPage = curPage;
        return $scope.previewTotalPages = totalPages;
      };
      $scope.loadProgress = function(state, loaded, total) {
        return $scope.previewLoading = state !== 'finished';
      };
      $scope.resultContent = function(document, size) {
        var modalInstance;
        $scope.resultContent = document.content;
        $scope.documentPrevName = document.name;
          /*
        return modalInstance = $modal.open({
          templateUrl: 'views/content.html',
          controller: 'ModalInstanceCtrl',
          size: size,
          scope: $scope
        });
          */
      };
      $scope.open = function(document, size) {
        var modalInstance;
        $scope.previewNotSupported = false;
        $scope.documentPrevName = document.name;
        $scope.document = document;
        $scope.documentPrevUrl = {};
        $scope.prev_url = document.preview_url;
        $scope.mimeType = document.mimetype;
        if (document.data_source_type === 'Alfresco') {
          $scope.documentPrevType = 'default';
          $scope.previewLoading = false;
          if ((document.preview_url !== void 0 && document.preview_url.indexOf("thumbnails/pdf") !== -1) || document.mimetype === "application/pdf") {
            $scope.documentPrevType = 'pdf';
          }
          if (document.preview_url !== void 0 && (document.preview_url.indexOf("thumbnails/imgpreview") !== -1 || document.mimetype.indexOf('image') > -1)) {
            $scope.documentPrevType = 'imgpreview';
          }
          $scope.documentPrevUrl = {
            url: SensefyAPIUrl + SensefyDocsPreview + '?docId=' + document.id,
            httpHeaders: {
              token: $scope.user.token
            }
          };
          if ($scope.documentPrevType === 'pdf') {
            $scope.previewLoading = true;
          }
          if ($scope.documentPrevType === 'imgpreview') {
            $scope.previewLoading = true;
            if (document.mimetype.indexOf('image/') === -1) {
              $scope.mimeType = 'image/png';
            }
            ApiService.get(SensefyPreviewDoc, {}, {
              docId: document.id,
              base64: true
            }, {
              token: $scope.user.token
            }).then(function(response) {
              $scope.documentPrevUrl.base64 = response.data;
              return $scope.previewLoading = false;
            });
          }
          $scope.document = document;
        } else {
          $scope.previewNotSupported = true;
        }
          /*
        return modalInstance = $modal.open({
          templateUrl: 'views/preview.html',
          controller: 'ModalInstanceCtrl',
          size: size,
          scope: $scope
        });
          */
      };

      if ($scope.queryTerm !== '') {
          angular.element('body').removeClass('ggl');
      }
      else{
          angular.element('body').addClass('ggl');
      }

      return $scope.hightlight = function($event) {
        return console.log($event.currentTarget);
      };
    }
  ]).controller('MltController', [
    '$scope', '$location', 'document', 'SemanticSearchService', 'SemanticMoreLikeThisService', '$translate', 'PDFViewerService', 'SensefyAPIUrl', 'SensefyDocsPreview', 'ApiService', 'SensefyPreviewDoc', '$rootScope', function($scope, $location, document, SemanticSearchService, SemanticMoreLikeThisService, $translate, pdf, SensefyAPIUrl, SensefyDocsPreview, ApiService, SensefyPreviewDoc, $rootScope) {
      var filterDocuments, normalizeDocumentsScore, sliderLabels;
      $scope.searchOptions = {
        rows: 10,
        fields: "id,score,name,title,mimetype,size,description,author,container_url,ds_creator,ds_creation_date,ds_last_modifier,ds_last_modified,url,preview_url,path,thumbnail_base64,data_source,data_source_type,language,image_length,image_width,Sharpness,focal_length,geo_lat,geo_long",
        start: 0
      };
      sliderLabels = {};
      $translate('txtLow').then(function(result) {
        return sliderLabels['low'] = result;
      });
      $translate('txtHigh').then(function(result) {
        return sliderLabels['high'] = result;
      });
      $scope.humanReadableSize = function(size) {
        var i, sizes;
        if (size) {
          sizes = ['B', 'KB', 'MB', 'GB', 'TB'];
          i = 0;
          if (typeof size === "string") {
            size = parseInt(size);
          }
          while (++i && size > 1000) {
            size = size / 1000;
          }
          return size.toFixed(2) + " " + sizes[i - 1];
        }
      };
      normalizeDocumentsScore = function(documents) {
        var max, min;
        min = 10000;
        max = 0;
        documents.forEach(function(doc) {
          if (doc.score < min) {
            min = doc.score;
          }
          if (doc.score > max) {
            return max = doc.score;
          }
        });
        documents.forEach(function(doc) {
          return doc.score = (doc.score - min) / (max - min);
        });
        return documents;
      };
      filterDocuments = function(documents, threshold) {
        var filtered;
        filtered = [];
        documents.forEach(function(doc) {
          if (doc.score >= threshold / 100) {
            return filtered.push(doc);
          }
        });
        return filtered;
      };
      $scope.documents = [];
      $scope.document = {};
      $scope.docId = document.id;
      $scope.init = false;
      $scope.loading = true;
      $scope.threshold = 70;
      $scope.searching = false;
      $scope.translate = function(value) {
        if (value === 0) {
          return sliderLabels['low'];
        } else if (value === 100) {
          return sliderLabels['high'];
        }
        return '';
      };
      $scope.isClose = false;
      $scope.resultContent = function(document, size) {
        var modalInstance;
        $scope.resultContent = document.content;
        $scope.documentPrevName = document.name;
          /*
        return modalInstance = $modal.open({
          templateUrl: 'views/content.html',
          controller: 'ModalInstanceCtrl',
          size: size,
          scope: $scope
        });
          */
      };
      $scope.open = function(document, size) {
        var childScope, modalInstance;
        childScope = $rootScope.$new(true, $scope);
        childScope.humanReadableSize = function(size) {
          var i, sizes;
          if (size) {
            sizes = ['B', 'KB', 'MB', 'GB', 'TB'];
            i = 0;
            while (++i && size > 1024) {
              size = size / 1024;
            }
            return size.toFixed(2) + " " + sizes[i - 1];
          }
        };
        childScope.viewer = pdf.Instance("viewer");
        childScope.previewNextPage = function() {
          return childScope.viewer.nextPage();
        };
        childScope.previewPreviousPage = function() {
          return childScope.viewer.prevPage();
        };
        childScope.pageLoaded = function(curPage, totalPages) {
          childScope.previewCurrentPage = curPage;
          return childScope.previewTotalPages = totalPages;
        };
        childScope.loadProgress = function(state, loaded, total) {
          return childScope.previewLoading = state !== 'finished';
        };
        childScope.previewNotSupported = false;
        childScope.documentPrevName = document.name;
        childScope.document = document;
        childScope.documentPrevUrl = {};
        childScope.prev_url = document.preview_url;
        childScope.mimeType = document.mimetype;
        if (document.data_source_type === 'Alfresco') {
          childScope.documentPrevType = 'default';
          childScope.previewLoading = false;
          if ((document.preview_url !== void 0 && document.preview_url.indexOf("thumbnails/pdf") !== -1) || document.mimetype === "application/pdf") {
            childScope.documentPrevType = 'pdf';
          }
          if (document.preview_url !== void 0 && (document.preview_url.indexOf("thumbnails/imgpreview") !== -1 || document.mimetype.indexOf('image') > -1)) {
            childScope.documentPrevType = 'imgpreview';
          }
          childScope.documentPrevUrl = {
            url: SensefyAPIUrl + SensefyDocsPreview + '?docId=' + document.id,
            httpHeaders: {
              token: $scope.user.token
            }
          };
          if ($scope.documentPrevType === 'pdf') {
            childScope.previewLoading = true;
          }
          if (childScope.documentPrevType === 'imgpreview') {
            childScope.previewLoading = true;
            if (document.mimetype.indexOf('image/') === -1) {
              childScope.mimeType = 'image/png';
            }
            ApiService.get(SensefyPreviewDoc, {}, {
              docId: document.id,
              base64: true
            }, {
              token: $scope.user.token
            }).then(function(response) {
              childScope.documentPrevUrl.base64 = response.data;
              return childScope.previewLoading = false;
            });
          }
        } else {
          childScope.previewNotSupported = true;
        }
        return modalInstance = $modal.open({
          templateUrl: 'views/preview.html',
          controller: 'ModalInstanceCtrl',
          size: size,
          scope: childScope
        });
      };
      $scope.initialize = function() {
        if (document.data.searchResults.numFound >= 1) {
          $scope.document = document.data.searchResults.documents[0];
        }
        return SemanticMoreLikeThisService.getSimilarDocuments($scope.document.id, $scope.user.token, $scope.searchOptions.fields).then(function(response) {
          $scope.allDocuments = normalizeDocumentsScore(response.data.searchResults.documents);
          return $scope.documents = filterDocuments($scope.allDocuments, $scope.threshold);
        }, function(response) {
          $scope.documents = [];
          return $scope.logout();
        })["finally"](function() {
          $scope.loading = false;
          return $scope.init = true;
        });
      };
      $scope.loadEntities = function(document) {
        return SemanticSearchService.getEntities(document.id, $scope.user.token, 0, 10, "id,label,thumbnail").then(function(response) {
          return document.entities = response.data.searchResults.documents || [];
        }, function(response) {
          document.entities = [];
          return $scope.logout();
        })["finally"](function() {
          return document.entitiesLoaded = true;
        });
      };
      $scope.initialize();
      return $scope.$watch('threshold', function(newValue, oldValue) {
        if ($scope.allDocuments !== void 0) {
          return $scope.documents = filterDocuments($scope.allDocuments, $scope.threshold);
        }
      });
    }
  ]).controller('ImageSearchController', [
    '$scope', 'ImageSearchService', function($scope, ImageSearchService) {
      var getFileContentAsDataURL, humanReadableSize, search;
      $scope.documents = [];
      $scope.numFound = null;
      $scope.selectedFile = null;
      $scope.selectedSearchField = 'cl_ha';
      $scope.searchFields = {
        'cl_ha': 'ColorLayout',
        'ph_ha': 'PHOG',
        'oh_ha': 'OpponentHistogram',
        'eh_ha': 'EdgeHistogram',
        'jc_ha': 'JCD',
        'sc_ha': 'Scalable Color',
        'ce_ha': 'CEDD',
        'fc_ha': 'FCTH'
      };
      getFileContentAsDataURL = function(file, selectedFile) {
        var reader;
        reader = new FileReader();
        reader.onloadend = function() {
          return selectedFile.dataURI = this.result;
        };
        return reader.readAsDataURL(file);
      };
      humanReadableSize = function(size) {
        var i, sizes;
        if (size) {
          sizes = ['B', 'KB', 'MB', 'GB', 'TB'];
          i = 0;
          while (++i && size > 1024) {
            size = size / 1024;
          }
          return size.toFixed(2) + " " + sizes[i - 1];
        }
      };
      search = function(file, field) {
        $scope.loading = true;
        return ImageSearchService.search(file, field, null).then(function(response) {
          $scope.documents = response.data.searchResults.documents;
          $scope.numFound = response.data.searchResults.numFound;
          return $scope.loading = false;
        }, function(response) {
          return $scope.logout();
        });
      };
      $scope.imageDropped = function() {
        var file;
        file = $scope.uploadedFile;
        $scope.selectedFile = {
          name: file.name,
          type: file.type,
          size: humanReadableSize(file.size)
        };
        getFileContentAsDataURL(file, $scope.selectedFile);
        return search(file, $scope.selectedSearchField, null);
      };
      return $scope.searchByField = function(field) {
        $scope.selectedSearchField = field;
        return search($scope.uploadedFile, $scope.selectedSearchField);
      };
    }
  ]).controller('ModalInstanceCtrl', [
    '$scope', '$modalInstance', function($scope, $modalInstance) {
      $scope.ok = function() {
        $modalInstance.close();
      };
      return $scope.cancel = function() {
        $modalInstance.dismiss('cancel');
      };
    }
  ]);
}.call(this));
(function() {
  angular.module('SensefyServices', [])
  .factory('ApiService', ['$log',
    'SensefyAPIUrl', '$http', '$q', function($log, SensefyAPIUrl, $http, $q) {
      return {
        request: function(method, path, data, params, headers) {
          var deferred;
          if (data == null) {
            data = {};
          }
          if (params == null) {
            params = {};
          }
          if (headers == null) {
            headers = {};
          }
          if ('POST' === method || 'PUT' === method) {
            if (data instanceof FormData) {
              headers = angular.extend(headers, {
                'Content-Type': void 0
              });
            } else {
              headers = angular.extend(headers, {
                'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
              });
              data = $.param(data);
            }
          }
          deferred = $q.defer();
          $log.info('url called is ' + SensefyAPIUrl + path);
          $log.info('data : ' + JSON.stringify(data));
          $log.info('params :' + JSON.stringify(params));
          $http({
            method: method,
            headers: headers,
            transformRequest: angular.identity,
            url: SensefyAPIUrl + path,
            data: data,
            params: params
          }).success(function(data, status, headers, config) {
            $log.info('success');
        	  return deferred.resolve({
              data: data,
              status: status,
              headers: headers,
              config: config
            });
          }).error(function(data, status, headers, config) {
        	  $log.info("error");
            return deferred.reject({
              data: data,
              status: status,
              headers: headers,
              config: config
            });
          });
          return deferred.promise;
        },
        "delete": function(path, data, params, headers) {
          if (headers == null) {
            headers = {};
          }
          return this.request('DELETE', path, data, params, headers);
        },
        get: function(path, data, params, headers) {
          if (params == null) {
            params = {};
          }
          if (headers == null) {
            headers = {};
          }
          params.semantic = false;
          return this.request('GET', path, data, params, headers);
        },
        post: function(path, data, params, headers) {
          if (headers == null) {
            headers = {};
          }
          return this.request('POST', path, data, params, headers);
        },
        put: function(path, data, params, headers) {
          if (headers == null) {
            headers = {};
          }
          return this.request('PUT', path, data, params, headers);
        },
        jsonp: function(path, data, params) {
          if (params == null) {
            params = {};
          }
          params.callback = 'JSON_CALLBACK';
          return this.request('jsonp', path, data, params);
        }
      };
    }
  ]).factory('LoginService', ['$log',
    'SensefyTokenCreatePath', 'ApiService', function($log, SensefyTokenCreatePath, ApiService) {
      return {
        login: function(username, password, params) {
          var parameters;
          if (params == null) {
            params = null;
          }
          parameters = {
            username: username,
            password: password
          };
          if (params != null) {
            angular.extend(parameters, params);
          }
          
          return ApiService.get(SensefyTokenCreatePath, {}, parameters);
        }
      };
    }
  ]).factory('SemanticSearchService', [
    'SensefySemanticSearchKeywordBased', 'SensefyEntityDrivenSearch', 'SensefyGetEntitiesByDoc', 'ApiService', function(SensefySemanticSearchKeywordBased, SensefyEntityDrivenSearch, SensefyGetEntitiesByDoc, ApiService) {
      return {
        search: function(query, token, start, rows, fields, filter, facet, sort, clustering) {
          if (start == null) {
            start = 0;
          }
          if (rows == null) {
            rows = 10;
          }
          if (fields == null) {
            fields = "*";
          }
          if (filter == null) {
            filter = [];
          }
          if (facet == null) {
            facet = true;
          }
          if (sort == null) {
            sort = "";
          }
          if (angular.isArray(filter)) {
            filter = filter.join(",");
          }
          return ApiService.get(SensefySemanticSearchKeywordBased, {}, {
            query: query,
            start: start,
            rows: rows,
            filter: filter,
            fields: fields,
            facet: facet,
            order: sort,
            spellcheck: true,
            clustering: clustering
          }, {
            token: token
          });
        },
        searchByEntity: function(entityId, token, start, rows, fields, filter, facet, sort, clustering) {
          if (start == null) {
            start = 0;
          }
          if (rows == null) {
            rows = 10;
          }
          if (fields == null) {
            fields = "*";
          }
          if (filter == null) {
            filter = [];
          }
          if (facet == null) {
            facet = true;
          }
          if (sort == null) {
            sort = "";
          }
          if (angular.isArray(filter)) {
            filter = filter.join(",");
          }
          return ApiService.get(SensefyEntityDrivenSearch, {}, {
            entityId: entityId,
            start: start,
            rows: rows,
            filter: filter,
            fields: fields,
            facet: facet,
            order: sort,
            clustering: clustering
          }, {
            token: token
          });
        },
        searchByEntityQuery: function(entityType, entityAttribute, entityAttributeValue, token, start, rows, fields, filter, facet, sort, clustering) {
          if (start == null) {
            start = 0;
          }
          if (rows == null) {
            rows = 10;
          }
          if (fields == null) {
            fields = "*";
          }
          if (filter == null) {
            filter = [];
          }
          if (facet == null) {
            facet = true;
          }
          if (sort == null) {
            sort = "";
          }
          if (angular.isArray(filter)) {
            filter = filter.join(",");
          }
          return ApiService.get(SensefyEntityDrivenSearch, {}, {
            entityType: entityType,
            entityAttribute: entityAttribute,
            entityAttributeValue: entityAttributeValue,
            start: start,
            rows: rows,
            filter: filter,
            fields: fields,
            facet: facet,
            order: sort,
            clustering: clustering
          }, {
            token: token
          });
        },
        searchByEntityType: function(entityTypeId, token, start, rows, fields, filter, facet, sort, clustering) {
          if (start == null) {
            start = 0;
          }
          if (rows == null) {
            rows = 10;
          }
          if (fields == null) {
            fields = "*";
          }
          if (filter == null) {
            filter = [];
          }
          if (facet == null) {
            facet = true;
          }
          if (sort == null) {
            sort = "";
          }
          if (angular.isArray(filter)) {
            filter = filter.join(",");
          }
          return ApiService.get(SensefyEntityDrivenSearch, {}, {
            entityType: entityTypeId,
            start: start,
            rows: rows,
            filter: filter,
            fields: fields,
            facet: facet,
            order: sort,
            clustering: clustering
          }, {
            token: token
          });
        },
        getEntities: function(docId, token, start, rows, fields, filter, facet, sort, clustering) {
          if (start == null) {
            start = 0;
          }
          if (rows == null) {
            rows = 10;
          }
          if (fields == null) {
            fields = "*";
          }
          if (filter == null) {
            filter = [];
          }
          if (facet == null) {
            facet = true;
          }
          if (sort == null) {
            sort = "";
          }
          return ApiService.get(SensefyGetEntitiesByDoc, {}, {
            docId: docId,
            start: start,
            rows: rows,
            filter: filter,
            fields: fields,
            facet: facet,
            order: sort,
            clustering: clustering
          }, {
            token: token
          });
        }
      };
    }
  ]).factory('SmartAutocompleteService', [
    'SensefySmartAutocompletePhase1', 'SensefySmartAutocompletePhase2', 'SensefySmartAutocompletePhase3', 'ApiService', function(SensefySmartAutocompletePhase1, SensefySmartAutocompletePhase2, SensefySmartAutocompletePhase3, ApiService) {
      return {
        phase1: function(termToComplete, token, numberOfSuggestions) {
          if (numberOfSuggestions == null) {
            numberOfSuggestions = 3;
          }
          return ApiService.get(SensefySmartAutocompletePhase1, {}, {
            termToComplete: termToComplete,
            numberOfSuggestions: numberOfSuggestions
          }, {
            token: token
          });
        },
        phase2: function(entityTypeId, termToComplete, token, numberOfSuggestions) {
          if (numberOfSuggestions == null) {
            numberOfSuggestions = 3;
          }
          return ApiService.get(SensefySmartAutocompletePhase2, {}, {
            entityTypeId: entityTypeId,
            termToComplete: termToComplete,
            numberOfSuggestions: numberOfSuggestions
          }, {
            token: token
          });
        },
        phase3: function(entityTypeId, entityTypeAttribute, termToComplete, token, numberOfSuggestions) {
          if (numberOfSuggestions == null) {
            numberOfSuggestions = 3;
          }
          return ApiService.get(SensefySmartAutocompletePhase3, {}, {
            entityTypeId: entityTypeId,
            entityAttributeField: entityTypeAttribute,
            termToComplete: termToComplete,
            numberOfSuggestions: numberOfSuggestions
          }, {
            token: token
          });
        }
      };
    }
  ]).factory('SemanticMoreLikeThisService', [
    'SensefyMlt', 'ApiService', function(SensefyMlt, ApiService) {
      return {
        getSimilarDocuments: function(docId, token, fields, rows) {
          if (fields == null) {
            fields = "*";
          }
          if (rows == null) {
            rows = 10;
          }
          return ApiService.get(SensefyMlt, {}, {
            docId: docId,
            fields: fields,
            rows: rows
          }, {
            token: token
          });
        }
      };
    }
  ]).factory('ImageSearchService', [
    'SensefyImageSearchPath', 'ApiService', function(SensefyImageSearchPath, ApiService) {
      return {
        search: function(file, field, token) {
          var formData, params;
          if (field == null) {
            field = null;
          }
          if (token == null) {
            token = null;
          }
          formData = new FormData();
          formData.append("file", file);
          params = {
            token: 'QPKR9AA3TNQ_YnErAnTlsUzblIw3Uaz6OF8bu7Mn-EaouAdVLraf3rNnTbFDco6zZb8XkAkXT9SPRQLc2KvEcYqELM8NOFjYGQ9KzrYVzYsr1FymSC619Jt9Rx76Dkr9iqyIvEsk-r5GMfERJ-9uifXF8lkRUZthUoCSglBQhYk'
          };
          if (field !== null) {
            params['field'] = field;
          }
          return ApiService.post(SensefyImageSearchPath, formData, params);
        }
      };
    }
  ]);
}.call(this));
(function() {
  angular.module('SensefyDirectives', []).directive('sensefyAutocomplete', [
    '$timeout', function($timeout) {
      return {
        restrict: 'EA',
        templateUrl: 'views/directive/sensefy-autocomplete/sensefy-autocomplete.html',
        replace: true,
        scope: {
          queryTerm: '=',
          queryFunction: '&'
        },
        controller: [
            '$scope',
            'SemanticSearchService',
            'SmartAutocompleteService',
            function($scope, SemanticSearchService, SmartAutocompleteService) {
          var phase1, phase2, phase3;
          $scope.suggestions = {};
          $scope.showSuggestions = false;
          $scope.selectedEntityTypeAttribute = null;
          $scope.selectedEntityTypeAttributeValue = null;
          $scope.selectedEntity = null;
          $scope.selectedEntityType = null;
          $scope.autocompletePhase = 'phase1';
          $scope.selectedTitleIndex = -1;
          $scope.originalAutocompleteQueryTerm = '';
          $scope.autocomplete = function($event) {
            if ($event == null) {
              $event = null;
            }
            if ($event !== null) {
              $event.stopPropagation();
            }
            if (($event != null ? $event.keyCode : void 0) === 13) {
              if ($scope.selectedTitleIndex >= 0) {
                $scope.titleSelected($scope.suggestions.titles[$scope.selectedTitleIndex]);
              } else {
                $scope.queryFunction();
              }
              if ($scope.queryTerm.length !== 0) {
                angular.element('header').removeClass('ggl');
              }
              return;
            }
            if (($event != null ? $event.keyCode : void 0) !== 40 && ($event != null ? $event.keyCode : void 0) !== 38) {
              switch ($scope.autocompletePhase) {
                case 'phase1':
                  phase1();
                  break;
                case 'phase2':
                  phase2();
                  break;
                case 'phase3':
                  phase3();
                  break;
                default:
                  return;
              }
            }
            return $scope.queryTerm = $scope.queryTerm;
          };
          phase1 = function() {
            if ($scope.queryTerm.length === 0) {
              return 0;
            }
            $scope.originalAutocompleteQueryTerm = $scope.queryTerm;
            return SmartAutocompleteService.phase1($scope.queryTerm, $scope.$parent.user.token).then(function(response) {
              if (response.data.header.status !== 200) {
                return;
              }
              $scope.selectedTitleIndex = -1;
              if (response.data.responseContent.entities) {
                $scope.suggestions['entities'] = response.data.responseContent.entities;
              }
              if (response.data.responseContent.entityTypes) {
                $scope.suggestions['entityTypes'] = response.data.responseContent.entityTypes;
              }
              if (response.data.responseContent.titles) {
                $scope.suggestions['titles'] = response.data.responseContent.titles;
              }
              if (response.data.responseContent.suggestions) {
                return $scope.suggestions['suggestions'] = response.data.responseContent.suggestions;
              }
            }, function(response) {
              return $scope.$parent.logout();
            });
          };
          phase2 = function() {
            return SmartAutocompleteService.phase2($scope.selectedEntityType.hierarchy.join(','), $scope.queryTerm, $scope.$parent.user.token).then(function(response) {
              if (response.data.responseContent.entities) {
                $scope.suggestions['entities'] = response.data.responseContent.entities;
              }
              if (response.data.responseContent.entityTypes) {
                $scope.suggestions['entityTypes'] = response.data.responseContent.entityTypes;
              }
              if (response.data.responseContent.suggestions) {
                return $scope.suggestions['suggestions'] = response.data.responseContent.suggestions;
              }
            }, function(response) {
              return $scope.$parent.logout();
            });
          };
          phase3 = function() {
            return SmartAutocompleteService.phase3($scope.selectedEntityType.id, $scope.selectedEntityTypeAttribute, $scope.queryTerm, $scope.$parent.user.token).then(function(response) {
              if (response.data.responseContent.entities) {
                $scope.suggestions['entities'] = response.data.responseContent.entities;
              }
              if (response.data.responseContent.entityTypes) {
                $scope.suggestions['entityTypes'] = response.data.responseContent.entityTypes;
              }
              if (response.data.responseContent.suggestions) {
                return $scope.suggestions['suggestions'] = response.data.responseContent.suggestions;
              }
            }, function(response) {
              return $scope.$parent.logout();
            });
          };
          $scope.entitySelected = function(entity) {
            $scope.selectedEntity = entity;
            $scope.queryTerm = entity.label;
            return $scope.$emit('entitySelected', entity);
          };
          $scope.entityTypeSelected = function(entityType) {
            $scope.selectedEntityType = entityType;
            $scope.selectedEntity = null;
            $scope.suggestions = {};
            $scope.queryTerm = '';
            $scope.autocompletePhase = 'phase2';
            $scope.$emit('entityTypeSelected', entityType);
            return $scope.autocomplete();
          };
          $scope.entityTypeAttributeSelected = function(attribute) {
            $scope.selectedEntityTypeAttribute = attribute;
            $scope.suggestions = {};
            $scope.autocompletePhase = 'phase3';
            $scope.queryTerm = '';
            $scope.$emit('entityTypeAttributeSelected', attribute);
            return $scope.autocomplete();
          };
          $scope.entityTypeAttributeValueSelected = function(value) {
            $scope.selectedEntityTypeAttributeValue = value;
            $scope.suggestions = {};
            $scope.queryTerm = '';
            $scope.selectedEntity = null;
            $scope.autocompletePhase = '';
            return $scope.$emit('entityTypeAttributeValueSelected', $scope.selectedEntityTypeAttribute, $scope.selectedEntityTypeAttributeValue);
          };
          $scope.titleSelected = function(title) {
            $scope.queryTerm = title.document_suggestion;
            return $scope.$emit('titleSelected', title);
          };
          $scope.suggestionSelected = function(suggestion) {
            $scope.queryTerm = suggestion;
            return $scope.$emit('suggestionSelected', suggestion);
          };
          $scope.cleanSuggestions = function() {
            return $scope.suggestions = {};
          };
          $scope.cleanEntityType = function() {
            $scope.selectedEntityType = null;
            $scope.selectedEntityTypeAttribute = null;
            $scope.selectedEntityTypeAttributeValue = null;
            $scope.queryTerm = "";
            $scope.autocompletePhase = "phase1";
            return $scope.$emit("entityTypeCleaned");
          };
          $scope.cleanEntityTypeAttribute = function() {
            $scope.selectedEntityTypeAttribute = null;
            $scope.selectedEntityTypeAttributeValue = null;
            $scope.queryTerm = '';
            $scope.autocompletePhase = 'phase2';
            return $scope.$emit('entityTypeAttributeCleaned');
          };
          return $scope.cleanEntityTypeAttributeValue = function() {
            $scope.selectedEntityTypeAttributeValue = null;
            $scope.queryTerm = '';
            $scope.autocompletePhase = 'phase3';
            return $scope.$emit('entityTypeAttributeValueCleaned');
          };
        }
        ],
        link: function(scope, element, attrs) {
          var fontSize, getWidthFilters, input, originalPaddingLeft, placeholder, span, suggestionsWrapper;
          input = element.find('input');
          span = element.find('div.ser-btn');
          originalPaddingLeft = input.css('paddingLeft');
          fontSize = input.css('fontSize');
          fontSize = parseInt(fontSize.substring(0, fontSize.length - 2));
          placeholder = input.attr('placeholder');
          suggestionsWrapper = element.find('.suggestions-wrapper');
          $timeout(function() {
            return input.focus();
          }, 0);
          input.on('focus', function() {
            if (scope.autocompletePhase !== '') {
              return suggestionsWrapper.fadeIn();
            }
          });
          input.on('blur', function() {
            scope.showSuggestions = false;
            if (scope.selectedTitleIndex >= 0) {
              scope.suggestions.titles[scope.selectedTitleIndex].selected = false;
            }
            scope.selectedTitleIndex = -1;
            return suggestionsWrapper.fadeOut();
          });
          input.on('keyup', function(event) {
            var maxTitles, valid;
            if ((event != null ? event.keyCode : void 0) === 27) {
              input.trigger('blur');
              return;
            }
            if ((event != null ? event.keyCode : void 0) === 13) {
              scope.showSuggestions = false;
              suggestionsWrapper.fadeOut();
              angular.element('body').removeClass('ggl');
            }
            if (event.keyCode === 40 || event.keyCode === 38) {
              valid = scope.suggestions.titles !== null && scope.suggestions.titles !== void 0 && scope.suggestions.titles.length > 0;
              if (!valid) {
                return;
              }
              maxTitles = scope.suggestions.titles.length - 1;
              if (scope.selectedTitleIndex >= 0) {
                scope.suggestions.titles[scope.selectedTitleIndex].selected = false;
              }
              if ((event != null ? event.keyCode : void 0) === 40) {
                scope.selectedTitleIndex = scope.selectedTitleIndex < maxTitles ? scope.selectedTitleIndex + 1 : -1;
              } else if ((event != null ? event.keyCode : void 0) === 38) {
                scope.selectedTitleIndex = scope.selectedTitleIndex > -1 ? scope.selectedTitleIndex - 1 : scope.selectedTitleIndex = maxTitles;
              }
              if (scope.selectedTitleIndex >= 0) {
                scope.suggestions.titles[scope.selectedTitleIndex].selected = true;
                scope.queryTerm = scope.suggestions.titles[scope.selectedTitleIndex].document_suggestion;
              }
              if (scope.selectedTitleIndex === -1) {
                scope.queryTerm = scope.originalAutocompleteQueryTerm;
              }
              return scope.$digest();
            }
          });
          span.on('keyup', function(event) {
            if ((event != null ? event.keyCode : void 0) === 13) {
              scope.showSuggestions = false;
              //if (scope.queryTerm.length !== 0) {
                angular.element('body').removeClass('ggl');
              //}
              return suggestionsWrapper.fadeOut();
            }
          });
          span.on('click', function() {
            //if (scope.queryTerm.length !== 0) {
            angular.element('body').removeClass('ggl');
            //}
          });
          scope.$on('titleSelected', function() {
            return angular.element('body').removeClass('ggl');
          });
          scope.$on('suggestionSelected', function() {
            return angular.element('body').removeClass('ggl');
          });
          scope.$on('entityTypeSelected', function(event, entityType) {
            var filtersWidth;
            suggestionsWrapper.fadeIn();
            filtersWidth = getWidthFilters();
            return scope.style = "padding-left: " + filtersWidth + "px";
          });
          scope.$on('entityTypeAttributeSelected', function(event) {
            var filtersWidth;
            filtersWidth = getWidthFilters();
            return scope.style = "padding-left: " + filtersWidth + "px";
          });
          getWidthFilters = function() {
            var accum;
            accum = 0;
            if (scope.selectedEntityType !== null) {
              accum += scope.selectedEntityType.type[0].length;
            }
            if (scope.selectedEntityTypeAttribute !== null) {
              accum += scope.selectedEntityTypeAttribute.length;
            }
            if (scope.selectedEntityTypeAttributeValue !== null) {
              accum += scope.selectedEntityTypeAttributeValue.length;
            }
            return accum = (accum * fontSize) / 2;
          };
          scope.$on('entityTypeAttributeValueSelected', function(event, attribute, value) {
            var filtersWidth;
            suggestionsWrapper.fadeOut();
            filtersWidth = getWidthFilters();
            input.prop('readonly', true);
            input.attr('placeholder', "");
            return scope.style = "padding-left: " + filtersWidth + "px";
          });
          scope.$on('entityTypeCleaned', function() {
            scope.cleanSuggestions();
            scope.style = "padding-left: " + originalPaddingLeft + "px";
            input.prop('readonly', false);
            return input.attr('placeholder', placeholder);
          });
          scope.$on('entityTypeAttributeCleaned', function() {
            var filtersWidth;
            scope.autocomplete();
            suggestionsWrapper.fadeIn();
            filtersWidth = getWidthFilters();
            scope.style = "padding-left: " + filtersWidth + "px";
            input.prop('readonly', false);
            return input.attr('placeholder', placeholder);
          });
          return scope.$on('entityTypeAttributeValueCleaned', function() {
            var filtersWidth;
            scope.autocomplete();
            suggestionsWrapper.fadeIn();
            filtersWidth = getWidthFilters();
            scope.style = "padding-left: " + filtersWidth + "px";
            input.prop('readonly', false);
            return input.attr('placeholder', placeholder);
          });
        }
      };
    }
  ]).directive("imagedrop", [
    '$parse', function($parse) {
      return {
        restrict: "EA",
        link: function(scope, element, attrs) {
          var loadFile, onDragEnd, onDragOver, onImageDrop;
          onImageDrop = $parse(attrs.onImageDrop);
          onDragOver = function(e) {
            e.preventDefault();
            return $(element).addClass("drag-over");
          };
          onDragEnd = function(e) {
            e.preventDefault();
            return $(element).removeClass("drag-over");
          };
          loadFile = function(file) {
            scope.uploadedFile = file;
            return scope.$apply(onImageDrop(scope));
          };
          $(element).bind("dragover", onDragOver);
          element.bind("dragleave", onDragEnd);
          return element.bind("drop", function(e) {
            onDragEnd(e);
            return loadFile(e.originalEvent.dataTransfer.files[0]);
          });
        }
      };
    }
  ]).directive("datasourceTab", [
    '$parse', function($parse) {
      return {
        restrict: "EA",
        link: function(scope, element, attrs, $index) {
          var colorArray, colorArrayLight, colorArrayLightRelation, cssClassName, style;
          colorArray = ['rgba(151,187,205,1)', 'rgba(70,191,189,1)', 'rgba(253,180,92,1)', 'rgba(247,70,74,1)', 'rgba(148,159,177,1)', 'rgba(120,209,35,1)'];
          colorArrayLight = ['rgba(151,187,205,.3)', 'rgba(70,191,189,.3)', 'rgba(253,180,92,.3)', 'rgba(247,70,74,.3)', 'rgba(148,159,177,.3)', 'rgba(120,209,35,.3)'];
          colorArrayLightRelation = ['rgba(151,187,205,.5)', 'rgba(70,191,189,.5)', 'rgba(253,180,92,.5)', 'rgba(247,70,74,.5)', 'rgba(148,159,177,.5)', 'rgba(120,209,35,.5)'];
          cssClassName = attrs.dynamicClass.replace(RegExp(' ', 'g'), '');
          element.attr('class', cssClassName);
          style = document.createElement('style');
          style.type = 'text/css';
          style.innerHTML += '.data-sources a.' + cssClassName + '{ background-color:' + colorArrayLight[scope.$index] + '; border-left:3px solid ' + colorArray[scope.$index] + ';  }';
          style.innerHTML += '.data-sources a.active.' + cssClassName + '{ background-color:' + colorArray[scope.$index] + '; border-left:3px solid ' + colorArray[scope.$index] + '; }';
          style.innerHTML += '#se-results .document.' + cssClassName + '{ border-left:2px solid ' + colorArrayLightRelation[scope.$index] + '; }';
          style.innerHTML += '#se-results .document.' + cssClassName + ':hover { border-left:2px solid ' + colorArray[scope.$index] + '; }';
          style.innerHTML += '#sensefy .' + cssClassName + ' .channel-d {color: ' + colorArray[scope.$index] + ';}';
          style.innerHTML += '.' + cssClassName + ' i.file, .'+cssClassName+' .hover-result {color: ' + colorArray[scope.$index] + ';}';
          /*
          element.bind("dragleave", onDragEnd)
          element.bind("drop", (e) ->
            onDragEnd(e)
            loadFile e.originalEvent.dataTransfer.files[0]
          )
          */

          return document.getElementsByTagName('head')[0].appendChild(style);
        }
      };
    }
  ]).directive("resultSet", [
    '$parse', function($parse) {
      return {
        restrict: "EA",
        link: function(scope, element, attrs, $index) {
          var cssRelationClassName;
          cssRelationClassName = attrs.datasourceRelation.replace(RegExp(' ', 'g'), '');
          return element.attr('class', 'link item  document  ' + cssRelationClassName);
          /*
          element.bind("dragleave", onDragEnd)
          element.bind("drop", (e) ->
            onDragEnd(e)
            loadFile e.originalEvent.dataTransfer.files[0]
          )
          */

        }
      };
    }
  ]);
}.call(this));
(function() {
  'use strict';
  angular.module('SensefyFilters', []).filter('excerpt', function() {
    return function(text, numCharacters, end) {
      if (numCharacters == null) {
        numCharacters = 100;
      }
      if (end == null) {
        end = '...';
      }
      if (text.length > numCharacters) {
        return text.substring(0, numCharacters) + end;
      } else {
        return text;
      }
    };
  }).filter('highlight', function() {
    return function(text, textToHighlight, element, clazz) {
      var escapeText, hlPieces, resultText;
      if (element == null) {
        element = 'span';
      }
      if (clazz == null) {
        clazz = 'hl';
      }
      escapeText = function(text) {
        var charToEscape, i, newText, _i, _len;
        charToEscape = ["*", ":", "?", "+", "[", "]", "(", ")"];
        newText = "";
        for (_i = 0, _len = text.length; _i < _len; _i++) {
          i = text[_i];
          newText += charToEscape.indexOf(text.charAt(i)) >= 0 ? "\\" + i : i;
        }
        return newText;
      };
      if (text === void 0 || text === null) {
        return text;
      }
      hlPieces = textToHighlight.split(" ");
      resultText = text;
      angular.forEach(hlPieces, function(piece, key) {
        var regExpStr, regularExp;
        regExpStr = "(?:^|\\b)(" + escapeText(piece) + ")";
        regularExp = new RegExp(regExpStr, "i");
        return resultText = resultText.replace(regularExp, function(match, p1, offset, all) {
          var str;
          return str = '<' + element + " class='" + clazz + "'>" + p1 + '</' + element + '>';
        });
      });
      return resultText;
    };
  }).filter("initials", function() {
    return function(text, maxSize) {
      var piece, pieces, result, _i, _len;
      if (maxSize == null) {
        maxSize = 2;
      }
      pieces = text.split(/\s+|-/);
      pieces = pieces.slice(0, maxSize);
      result = "";
      for (_i = 0, _len = pieces.length; _i < _len; _i++) {
        piece = pieces[_i];
        result += piece.substring(0, 1).toUpperCase();
      }
      return result;
    };
  }).filter("sanitizeIdForURI", [
      '$window',
      function($window) {
        return function(identifier) {
            var encoded, regExp;
            encoded = $window.encodeURI(identifier);
            regExp = new RegExp("\/", "g");
            return encoded.replace(regExp, '[SL]');
    };
  }
]);
}.call(this));
(function() {
  angular.module('SalzburgDemo', [
      'SensefyConfiguration',
      'SensefyControllers',
      'SensefyDirectives',
      'SensefyFilters',
      'SensefyServices',
      'ngAnimate',
      'rzModule',
      'ngPDFViewer',
      'angularify.semantic.dropdown',
      'angularUtils.directives.dirPagination']).config([
    '$provide', function($provide) {
      return $provide.decorator('pdfviewerDirective', [
        '$delegate', function($delegate) {
          if (($delegate != null ? $delegate.length : void 0) > 0) {
            $delegate[0].scope.src = '=';
          }
          return $delegate;
        }
      ]);
    }
  ]);
}.call(this));