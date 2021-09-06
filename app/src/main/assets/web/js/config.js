let url = 'https://ctc.daqsoft.com/test/api/rest/',
    region = '450100'
function getUrlparams(name) {
    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.href) || [, ""])[1].replace(/\+/g, '%20')) || null
}