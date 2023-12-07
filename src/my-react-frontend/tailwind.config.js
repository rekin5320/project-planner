/** @type {import('tailwindcss').Config} */
module.exports = {
    content: ["./src/**/*.{js,jsx,ts,tsx}"],
    theme: {
        extend: {
            colors: {
                "custom-pink": "#ef959d",
                "custom-gray": "#60656f",
                "custom-blue": "#b7c9e2",
                "custom-lightgray": "#e6e6e6",
            },
        },
    },
    plugins: [],
}
