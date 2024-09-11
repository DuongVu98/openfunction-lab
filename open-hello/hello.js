export const helloESM = (req, res) => {
    console.log(`Received a request from ${req.ip}`);
    res.send("Hello from ECMAScript module function!");
};