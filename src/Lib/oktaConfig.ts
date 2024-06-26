
export const oktaConfig = {
    clientId: '0oahxbj0dxTDNvgRY5d7',
    issuer: 'https://dev-98514384.okta.com/oauth2/default',
    redirectUri: 'http://localhost:3000/login/callback',
    scopes: ['openid', 'profile', 'email'],
    pkce: true,
    disableHttpsCheck: true,
}