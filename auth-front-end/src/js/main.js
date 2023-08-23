export const API_BASE_URL = 'http://localhost:8080/app';

export function existLoggedUser() {
    return !!(localStorage.getItem("username"));
}