import JsonApiQueryParserClass from 'jsonapi-query-parser';

const queryParser = new JsonApiQueryParserClass();

export const parseQuerySpec = (req, res) => {
    const data = req.body;
    console.log(`Request URL: ${JSON.stringify(data)}`);
    let requestData = queryParser.parseRequest(data.url);
    res.send(requestData);
};