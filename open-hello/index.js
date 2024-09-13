import JsonApiQueryParserClass from 'jsonapi-query-parser';

const queryParser = new JsonApiQueryParserClass();

export const parseQuerySpec = (req, res) => {
    let requestData = queryParser.parseRequest(req.data.url);
    res.send(requestData);
};