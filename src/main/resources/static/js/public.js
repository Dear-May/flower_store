function getCookie(name) {
    const nameEQ = name + "=";
    const ca = document.cookie.split(';');
    for(let i = 0; i < ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) === ' ') c = c.substring(1, c.length);
        if (c.indexOf(nameEQ) === 0) return c.substring(nameEQ.length, c.length);
    }
    return null;
}
document.addEventListener('DOMContentLoaded', function () {
    // 检查cookie中是否有用户信息

    const sessionUsername = sessionStorage.getItem('username');
    const cookieUsername = getCookie('username');
    if (cookieUsername || sessionUsername) {
        const userNav = document.getElementById('userNav');
        userNav.textContent = '个人中心';
        userNav.href = '/user/userInfo';
    }
    else {
        window.location.href = '/user/login'
    }
})
